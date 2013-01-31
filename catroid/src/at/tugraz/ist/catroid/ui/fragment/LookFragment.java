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
import at.tugraz.ist.catroid.formulaeditor.CatKeyEvent;
import at.tugraz.ist.catroid.formulaeditor.FormulaEditorEditText;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;

public class LookFragment extends SherlockListFragment implements Dialog.OnKeyListener {

	public final static String LOOK_FRAGMENT_TAG = "lookFragment";

	private FormulaEditorEditText mFormulaEditorEditText;
	private final Integer[] lookResourceIds = { R.string.formula_editor_look_x, R.string.formula_editor_look_y,
			R.string.formula_editor_look_ghosteffect, R.string.formula_editor_look_brightness,
			R.string.formula_editor_look_size, R.string.formula_editor_look_rotation,
			R.string.formula_editor_look_layer };

	@Override
	public void onListItemClick(ListView listView, View view, int position, long id) {
		Log.v("info", "touch of listitem " + getString(lookResourceIds[position]) + " with position: " + position);

		mFormulaEditorEditText.handleKeyEvent(new CatKeyEvent(CatKeyEvent.ACTION_DOWN, CatKeyEvent.KEYCODE_LOOK_X
				+ position));
		KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK);
		onKey(null, keyEvent.getKeyCode(), keyEvent);
	}

	public LookFragment(FormulaEditorEditText formulaEditorEditText) {
		mFormulaEditorEditText = formulaEditorEditText;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		String[] items = new String[lookResourceIds.length];
		int index = 0;
		for (Integer lookResourceID : lookResourceIds) {
			items[index] = getString(lookResourceID);
			index++;

		}
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, items);
		setListAdapter(arrayAdapter);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		super.onPrepareOptionsMenu(menu);
		ActionBar actionBar = getSherlockActivity().getSupportActionBar();
		actionBar.setTitle(getString(R.string.formula_editor_choose_look_variable));
		actionBar.setDisplayHomeAsUpEnabled(false);
	}

	@Override
	public void onSaveInstanceState(Bundle saveInstanceState) {
		saveInstanceState.putBoolean("restoreInstance", true);
		super.onSaveInstanceState(saveInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.fragment_formula_editor_list, container, false);
		return fragmentView;
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
		fragTransaction.add(android.R.id.tabhost, this, LookFragment.LOOK_FRAGMENT_TAG);
		fragTransaction.commit();
	}

	@Override
	public boolean onKey(DialogInterface d, int keyCode, KeyEvent event) {
		Log.i("info", "onKey() in LookFragment! keyCode: " + keyCode);
		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				Log.i("info", "KEYCODE_BACK pressed in LookFragment!");
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

}
