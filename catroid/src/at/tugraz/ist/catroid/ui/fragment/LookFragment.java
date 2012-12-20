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
import at.tugraz.ist.catroid.formulaeditor.CatKeyboardView;

import com.actionbarsherlock.app.SherlockListFragment;

public class LookFragment extends SherlockListFragment implements Dialog.OnKeyListener {

	public final static String LOOK_FRAGMENT_TAG = "lookFragment";

	private CatKeyboardView catKeyboardView;
	private final Integer[] lookResourceIds = { R.string.formula_editor_look_x, R.string.formula_editor_look_y,
			R.string.formula_editor_look_ghosteffect, R.string.formula_editor_look_brightness,
			R.string.formula_editor_look_size, R.string.formula_editor_look_rotation,
			R.string.formula_editor_look_layer };
	private static final int CANCEL_INDEX = -2;

	//	public void onClick(DialogInterface dialog, int index) {
	//		if (index == CANCEL_INDEX) {
	//
	//			return;
	//		}
	//		Log.v("touched: ", "" + index);
	//		Log.v("touched: ", getString(lookResourceIds[index]));
	//
	//		int[] keyCode = new int[1];
	//		keyCode[0] = 0;
	//
	//		catKeyboardView.onKey(CatKeyEvent.KEYCODE_LOOK_X + index, keyCode);
	//
	//	}

	@Override
	public void onListItemClick(ListView listView, View view, int position, long id) {
		Log.v("info", "touch of listitem " + getString(lookResourceIds[position]) + " with position: " + position);

		int[] keyCode = new int[1];
		keyCode[0] = 0;
		catKeyboardView.onKey(CatKeyEvent.KEYCODE_LOOK_X + position, keyCode);
		KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK);
		onKey(null, keyEvent.getKeyCode(), keyEvent);

		//		super.onListItemClick(l, v, position, id);
	}

	public static LookFragment newInstance() { // TODO change this!!! o.o

		LookFragment fragment = new LookFragment();

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		//		if (savedInstanceState != null) {
		//			restoreInstance = savedInstanceState.getBoolean("restoreInstance");
		//		}
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		//		setRetainInstance(true);

		String[] lookNames = new String[lookResourceIds.length];
		int index = 0;
		for (Integer lookResourceID : lookResourceIds) {
			lookNames[index] = getString(lookResourceID);
			index++;

		}
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, lookNames);
		setListAdapter(arrayAdapter);
		getSherlockActivity().getSupportActionBar().setTitle(getString(R.string.formula_editor_choose_look_variable));
	}

	@Override
	public void onSaveInstanceState(Bundle saveInstanceState) {
		saveInstanceState.putBoolean("restoreInstance", true);
		super.onSaveInstanceState(saveInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View fragmentView = inflater.inflate(R.layout.fragment_look, container, false);

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
		//		fragTransaction.hide(formulaEditorFragment);
		//		fragTransaction.show(this);
		//		fragTransaction.addToBackStack(null);

		fragTransaction.commit();
		//		activity.findViewById(R.id.fragment_formula_editor).setVisibility(View.GONE);
		//		activity.findViewById(R.id.fragment_look).setVisibility(View.VISIBLE);
	}

	public void setCatKeyboardView(CatKeyboardView catKeyboardView) {
		this.catKeyboardView = catKeyboardView;

	}

	@Override
	public boolean onKey(DialogInterface d, int keyCode, KeyEvent event) {
		Log.i("info", "onKey() in LookFragment! keyCode: " + keyCode);
		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				Log.i("info", "KEYCODE_BACK pressed in LookFragment!");
				FragmentTransaction fragTransaction = getSherlockActivity().getSupportFragmentManager()
						.beginTransaction();
				FormulaEditorFragment formulaEditorFragment = (FormulaEditorFragment) getSherlockActivity()
						.getSupportFragmentManager().findFragmentByTag(
								FormulaEditorFragment.FORMULA_EDITOR_FRAGMENT_TAG);

				fragTransaction.remove(this);
				fragTransaction.show(formulaEditorFragment);
				fragTransaction.commit();
				return true;

			default:
				break;
		}
		return false;
	}

}
