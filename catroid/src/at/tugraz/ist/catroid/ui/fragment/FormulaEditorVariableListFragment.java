/**
 *  Catroid: An on-device graphical programming language for Android devices
 *  Copyright (C) 2010-2011 The Catroid Team
 *  (<http://code.google.com/p/catroid/wiki/Credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *  
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://www.catroid.org/catroid_license_additional_term
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *   
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package at.tugraz.ist.catroid.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import at.tugraz.ist.catroid.R;
import at.tugraz.ist.catroid.formulaeditor.FormulaEditorEditText;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class FormulaEditorVariableListFragment extends SherlockListFragment implements Dialog.OnKeyListener {

	public static final String VARIABLE_TAG = "variableFragment";

	private final String mTag;
	private String[] mItems = { "EASY", "2easy", "3rd", "4th" };
	private FormulaEditorEditText mFormulaEditorEditText;
	private String mActionBarTitle;
	private com.actionbarsherlock.view.ActionMode mContextActionMode;
	private int mDeleteIndex;

	public FormulaEditorVariableListFragment(FormulaEditorEditText formulaEditorEditText, String actionBarTitle,
			String fragmentTag) {
		mFormulaEditorEditText = formulaEditorEditText;
		mActionBarTitle = actionBarTitle;
		mTag = fragmentTag;

		mContextActionMode = null;
		mDeleteIndex = -1;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, mItems);
		setListAdapter(arrayAdapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.fragment_formula_editor_list, container, false);
		return fragmentView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_formulaeditor_variablelist, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
		Log.i("info", "FEVLF.onCreateContextMenu()");
		super.onCreateContextMenu(menu, view, menuInfo);
		getSherlockActivity().getMenuInflater().inflate(R.menu.menu_formulaeditor_variablelist, menu);

	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {

		super.onPrepareOptionsMenu(menu);
		//		menu.removeItem(R.id.menu_add);
		//		menu.removeItem(R.id.menu_start);
		ActionBar actionBar = getSherlockActivity().getSupportActionBar();
		actionBar.setTitle(mActionBarTitle);
		actionBar.setDisplayHomeAsUpEnabled(false);
	}

	//	@Override
	//	public void onListItemClick(ListView listView, View view, int position, long id) {
	//		//		mFormulaEditorEditText.handleKeyEvent(new CatKeyEvent(CatKeyEvent.ACTION_DOWN, mOffset + position));
	//		//		KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK);
	//		//		onKey(null, keyEvent.getKeyCode(), keyEvent);
	//	}

	@Override
	public void onStart() {
		registerForContextMenu(getListView());

		//		getListView().setOnItemClickListener(new OnItemClickListener() {
		//			@Override
		//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		//				// TODO 
		//			}
		//		});

		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id) {
				Log.i("info", "onItemLongClick()");
				if (mContextActionMode == null) {
					mItems[position] = "itemLongClick";
					mDeleteIndex = position;
					//					arg0.setPressed(true);
					//					arg0	.setBackgroundResource(R.color.backbrown);
					//					mContextActionMode = getSherlockActivity().startActionMode(mContextModeCallback);
					ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
							android.R.layout.simple_list_item_1, mItems);
					setListAdapter(arrayAdapter);
					getSherlockActivity().openContextMenu(getListView());
					return true;
				}
				return false;
			}
		});

		getListView().setFocusable(true);
		getListView().setLongClickable(true);

		super.onStart();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_delete:
				mContextActionMode = getSherlockActivity().startActionMode(mContextModeCallback);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	//	doesnt get called from framework
	//	@Override
	//	public boolean onContextItemSelected(android.view.MenuItem item) {
	//		Log.i("info", "FEVLF.onContextItemSelected");
	//		switch (item.getItemId()) {
	//			case R.id.menu_delete:
	//				mItems[mDeleteIndex] = "del";
	//				return true;
	//			default:
	//				return super.onContextItemSelected(item);
	//		}
	//
	//	}

	public void showFragment(Context context) {
		FragmentActivity activity = (FragmentActivity) context;
		FragmentManager fragmentManager = activity.getSupportFragmentManager();
		FragmentTransaction fragTransaction = fragmentManager.beginTransaction();

		Fragment formulaEditorFragment = fragmentManager
				.findFragmentByTag(FormulaEditorFragment.FORMULA_EDITOR_FRAGMENT_TAG);
		fragTransaction.hide(formulaEditorFragment);
		//		fragTransaction.replace(R.id.fragment_formula_editor, this);
		//		fragTransaction.remove(formulaEditorFragment);
		fragTransaction.add(android.R.id.tabhost, this, mTag);
		fragTransaction.commit();

	}

	@Override
	public boolean onKey(DialogInterface d, int keyCode, KeyEvent event) {
		Log.i("info", "onKey() in FE-ListFragment! keyCode: " + keyCode);
		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				Log.i("info", "KEYCODE_BACK pressed in FE-ListFragment!");
				FragmentTransaction fragTransaction = getSherlockActivity().getSupportFragmentManager()
						.beginTransaction();
				fragTransaction.remove(this);
				fragTransaction.show(getSherlockActivity().getSupportFragmentManager().findFragmentByTag(
						FormulaEditorFragment.FORMULA_EDITOR_FRAGMENT_TAG));
				fragTransaction.commit();
				return true;
			default:
				break;
		}
		return false;
	}

	private ActionMode.Callback mContextModeCallback = new ActionMode.Callback() {

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			//			mode.getMenuInflater().inflate(R.menu.menu_formulaeditor_variablelist, menu);
			//			mode.setTitle("Delete");
			//			mode.setSubtitle("SubTitle");

			//			View doneButton = getSherlockActivity().findViewById(R.id.abs__action_mode_close_button);
			//			doneButton.setOnClickListener(new View.OnClickListener() {
			//				@Override
			//				public void onClick(View v) {
			//					mItems[mIndexContextActionMode] = "del";
			//					mContextActionMode.finish();
			//				}
			//			});

			menu.removeItem(R.id.menu_delete);

			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch (item.getItemId()) {
			//				case R.id.menu_delete:
			//					Log.i("info", "mContextModeCallback.nActionItemClicked()");
			//					// TODO
			//					mode.finish();
			//					return true;

				default:
					Log.i("info", (String) item.getTitle());
					return false;
			}
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			mContextActionMode = null;
		}

	};

}
