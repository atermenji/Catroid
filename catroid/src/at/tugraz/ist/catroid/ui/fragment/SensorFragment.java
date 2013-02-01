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
import at.tugraz.ist.catroid.formulaeditor.CatKeyEvent;
import at.tugraz.ist.catroid.formulaeditor.FormulaEditorEditText;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;

public class SensorFragment extends SherlockListFragment implements Dialog.OnKeyListener {

	public final static String SENSOR_FRAGMENT_TAG = "sensorFragment";

	private final int[] itemsId = { R.string.formula_editor_sensor_x_acceleration,
			R.string.formula_editor_sensor_y_acceleration, R.string.formula_editor_sensor_z_acceleration,
			R.string.formula_editor_sensor_azimuth_orientation, R.string.formula_editor_sensor_pitch_orientation,
			R.string.formula_editor_sensor_roll_orientation };

	private FormulaEditorEditText mFormulaEditorEditText;

	@Override
	public void onListItemClick(ListView listView, View view, int position, long id) {

		mFormulaEditorEditText.handleKeyEvent(new CatKeyEvent(CatKeyEvent.ACTION_DOWN, CatKeyEvent.KEYCODE_SENSOR1
				+ position));
		KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK);
		onKey(null, keyEvent.getKeyCode(), keyEvent);
	}

	public SensorFragment(FormulaEditorEditText formulaEditorEditText) {
		mFormulaEditorEditText = formulaEditorEditText;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		String[] items = new String[itemsId.length];
		int index = 0;
		for (Integer item : itemsId) {
			items[index] = getString(item);
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
		actionBar.setTitle(R.string.formula_editor_math);
		actionBar.setDisplayHomeAsUpEnabled(false);
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
		fragTransaction.add(android.R.id.tabhost, this, SENSOR_FRAGMENT_TAG);
		fragTransaction.commit();
	}

	@Override
	public boolean onKey(DialogInterface d, int keyCode, KeyEvent event) {
		Log.i("info", "onKey() in SensorFragment! keyCode: " + keyCode);
		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				Log.i("info", "KEYCODE_BACK pressed in SensorFragment!");
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
