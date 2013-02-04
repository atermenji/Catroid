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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
	private String[] mItems = { "EASY" };
	private FormulaEditorEditText mFormulaEditorEditText;
	private String mActionBarTitle;
	private com.actionbarsherlock.view.ActionMode mActionMode;

	public FormulaEditorVariableListFragment(FormulaEditorEditText formulaEditorEditText, String actionBarTitle,
			String fragmentTag) {
		mFormulaEditorEditText = formulaEditorEditText;
		mActionBarTitle = actionBarTitle;
		mTag = fragmentTag;

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
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_formulaeditor_variablelist, menu);
		super.onCreateOptionsMenu(menu, inflater);
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_delete:
				mItems[0] = "gg";
				ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
						android.R.layout.simple_list_item_1, mItems);
				setListAdapter(arrayAdapter);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position, long id) {
		//		mFormulaEditorEditText.handleKeyEvent(new CatKeyEvent(CatKeyEvent.ACTION_DOWN, mOffset + position));
		//		KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK);
		//		onKey(null, keyEvent.getKeyCode(), keyEvent);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.fragment_formula_editor_list, container, false);
		return fragmentView;
	}

	@Override
	public void onStart() {
		getView().setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View view) {
				if (mActionMode == null) {
					mActionMode = getSherlockActivity().startActionMode(mActionModeCallback);
					view.setSelected(true);
					return true;
				}
				return false;
			}
		});
		super.onStart();
	}

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

	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			mode.getMenuInflater().inflate(R.menu.menu_formulaeditor_variablelist, menu);
			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch (item.getItemId()) {
				case R.id.menu_delete:
					// TODO
					mode.finish();
					return true;
				default:
					return false;
			}
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			// TODO
		}

	};

}
