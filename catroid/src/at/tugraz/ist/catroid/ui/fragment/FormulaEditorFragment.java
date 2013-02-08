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

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import at.tugraz.ist.catroid.ProjectManager;
import at.tugraz.ist.catroid.R;
import at.tugraz.ist.catroid.content.bricks.Brick;
import at.tugraz.ist.catroid.formulaeditor.Formula;
import at.tugraz.ist.catroid.formulaeditor.FormulaEditorEditText;
import at.tugraz.ist.catroid.formulaeditor.FormulaElement;
import at.tugraz.ist.catroid.formulaeditor.InternFormulaParser;
import at.tugraz.ist.catroid.utils.Utils;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class FormulaEditorFragment extends SherlockFragment implements OnKeyListener {

	private static final int PARSER_OK = -1;
	private static final int PARSER_STACK_OVERFLOW = -2;
	private static final int PARSER_INPUT_SYNTAX_ERROR = -3;

	private static final int SET_FORMULA_ON_CREATE_VIEW = 0;
	private static final int SET_FORMULA_ON_SWITCH_EDIT_TEXT = 1;
	private static final int TIME_WINDOW = 2000;

	public static final String FORMULA_EDITOR_FRAGMENT_TAG = "formula_editor_fragment";

	private Context context;

	private Brick currentBrick;
	private Formula currentFormula;
	private FormulaEditorEditText formulaEditorEditText;
	private LinearLayout catKeyboardView;
	private LinearLayout brickSpace;
	private View brickView;
	private long[] confirmBackTimeStamp = { 0, 0 };
	private long[] confirmSwitchEditTextTimeStamp = { 0, 0 };
	private int confirmBackCounter = 0;
	private int confirmSwitchEditTextCounter = 0;

	public boolean restoreInstance = false;

	public FormulaEditorFragment(Brick brick, Formula formula) {
		currentBrick = brick;
		currentFormula = formula;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			restoreInstance = savedInstanceState.getBoolean("restoreInstance");
		}
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		getSherlockActivity().getSupportActionBar().setTitle(getString(R.string.formula_editor_title));
	}

	@Override
	public void onSaveInstanceState(Bundle saveInstanceState) {
		saveInstanceState.putBoolean("restoreInstance", true);
		super.onSaveInstanceState(saveInstanceState);
	}

	public static void showFragment(View view, Brick brick, Formula formula) {

		SherlockFragmentActivity activity = null;
		activity = (SherlockFragmentActivity) view.getContext();

		FormulaEditorFragment formulaEditorDialog = (FormulaEditorFragment) activity.getSupportFragmentManager()
				.findFragmentByTag(FORMULA_EDITOR_FRAGMENT_TAG);

		if (formulaEditorDialog == null) {
			formulaEditorDialog = new FormulaEditorFragment(brick, formula);
			FragmentManager fragmentManager = activity.getSupportFragmentManager();
			FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
			//			fragTransaction.remove(fragmentManager.findFragmentById(android.R.id.tabs));
			fragTransaction.replace(android.R.id.tabhost, formulaEditorDialog, FORMULA_EDITOR_FRAGMENT_TAG);
			fragTransaction.commit();

		} else {
			formulaEditorDialog.setInputFormula(formula, SET_FORMULA_ON_SWITCH_EDIT_TEXT);
		}

	}

	private void onUserDismiss() { //dont override onDismiss, this must not be called on orientation change
		formulaEditorEditText.endEdit();
		currentFormula.prepareToRemove();
		//		currentFormula = null;
		//		currentBrick = null;

		SherlockFragmentActivity activity = getSherlockActivity();
		FragmentManager fragmentManager = activity.getSupportFragmentManager();
		FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
		fragTransaction.remove(fragmentManager.findFragmentByTag(FORMULA_EDITOR_FRAGMENT_TAG));
		fragTransaction.commit();
		activity.getSupportActionBar().setTitle(
				getString(R.string.sprite_name) + " " + ProjectManager.getInstance().getCurrentSprite().getName());

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View dialogView = inflater.inflate(R.layout.fragment_formula_editor, container, false);
		dialogView.setFocusableInTouchMode(true);
		dialogView.requestFocus();

		context = getActivity();
		brickSpace = (LinearLayout) dialogView.findViewById(R.id.formula_editor_brick_space);
		if (brickSpace != null) {
			brickView = currentBrick.getView(context, 0, null);
			brickSpace.addView(brickView);
		}

		formulaEditorEditText = (FormulaEditorEditText) dialogView.findViewById(R.id.formula_editor_edit_field);
		if (brickSpace != null) {
			brickSpace.measure(0, 0);
		}
		catKeyboardView = (LinearLayout) dialogView.findViewById(R.id.formula_editor_keyboard);
		//		catKeyboardView.setFormulaEditText(formulaEditorEditText);

		if (brickSpace != null) {
			formulaEditorEditText.init(this, brickSpace.getMeasuredHeight(), catKeyboardView);
		} else {
			formulaEditorEditText.init(this, 0, catKeyboardView);
		}
		setInputFormula(currentFormula, SET_FORMULA_ON_CREATE_VIEW);

		return dialogView;
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);

		menu.clear();
		// TODO delete artefacts from old FE actionbar.
		//		getSherlockActivity().getSupportMenuInflater().inflate(R.menu.menu_formula_editor, menu);
		getSherlockActivity().getSupportActionBar().setTitle(getString(R.string.formula_editor_title));

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case android.R.id.home:
				endFormulaEditor();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onContextItemSelected(android.view.MenuItem item) {
		Log.i("", "FEF.onContextItemSelected");
		return super.onContextItemSelected(item);
	}

	public void setInputFormula(Formula newFormula, int mode) {

		int orientation = getResources().getConfiguration().orientation;

		switch (mode) {
			case SET_FORMULA_ON_CREATE_VIEW:
				currentFormula.removeTextFieldHighlighting(brickView, orientation);
				formulaEditorEditText.enterNewFormula(currentFormula.getInternFormulaState());
				currentFormula.highlightTextField(brickView, orientation);
				refreshFormulaPreviewString(formulaEditorEditText.getText().toString());
				break;
			case SET_FORMULA_ON_SWITCH_EDIT_TEXT:

				if (currentFormula == newFormula && formulaEditorEditText.hasChanges()) {
					formulaEditorEditText.quickSelect();
					break;
				}
				if (formulaEditorEditText.hasChanges()) {
					confirmSwitchEditTextTimeStamp[0] = confirmSwitchEditTextTimeStamp[1];
					confirmSwitchEditTextTimeStamp[1] = System.currentTimeMillis();
					confirmSwitchEditTextCounter++;
					if (!saveFormulaIfPossible()) {
						return;
					}
				}
				if (currentFormula != null) {
					currentFormula.refreshTextField(brickView);
				}

				formulaEditorEditText.endEdit();
				currentFormula.removeTextFieldHighlighting(brickView, orientation);
				currentFormula = newFormula;
				currentFormula.highlightTextField(brickView, orientation);
				formulaEditorEditText.enterNewFormula(newFormula.getInternFormulaState());
				break;
			default:
				break;
		}
	}

	public boolean saveFormulaIfPossible() {
		InternFormulaParser formulaToParse = formulaEditorEditText.getFormulaParser();
		FormulaElement formulaParseTree = formulaToParse.parseFormula();
		int err = formulaToParse.getErrorTokenIndex();
		switch (err) {
			case PARSER_OK:
				currentFormula.setRoot(formulaParseTree);
				if (brickSpace != null) {
					currentFormula.refreshTextField(brickView);
				}
				formulaEditorEditText.formulaSaved();
				showToast(R.string.formula_editor_changes_saved);
				return true;
			case PARSER_STACK_OVERFLOW:
				return checkReturnWithoutSaving(PARSER_STACK_OVERFLOW);
			default:
				formulaEditorEditText.setParseErrorCursorAndSelection();//TODO change name and functionality to show error tokens
				return checkReturnWithoutSaving(PARSER_INPUT_SYNTAX_ERROR);
		}
	}

	private boolean checkReturnWithoutSaving(int errorType) {
		Log.i("info", "confirmBackCounter=" + confirmBackCounter + " "
				+ (System.currentTimeMillis() <= confirmBackTimeStamp[0] + TIME_WINDOW)
				+ " confirmSwitchEditTextCounter=" + confirmSwitchEditTextCounter + " "
				+ (System.currentTimeMillis() <= confirmSwitchEditTextTimeStamp[0] + TIME_WINDOW));

		if (((System.currentTimeMillis() <= confirmBackTimeStamp[0] + TIME_WINDOW) && (confirmBackCounter > 1))
				|| ((System.currentTimeMillis() <= confirmSwitchEditTextTimeStamp[0] + TIME_WINDOW) && (confirmSwitchEditTextCounter > 1))) {
			confirmSwitchEditTextTimeStamp[0] = 0;
			confirmSwitchEditTextTimeStamp[1] = 0;
			confirmSwitchEditTextCounter = 0;
			confirmBackTimeStamp[0] = 0;
			confirmBackTimeStamp[1] = 0;
			confirmBackCounter = 0;
			showToast(R.string.formula_editor_changes_discarded);
			return true;
		} else {
			switch (errorType) {
				case PARSER_INPUT_SYNTAX_ERROR:
					showToast(R.string.formula_editor_parse_fail);
					break;
				case PARSER_STACK_OVERFLOW:
					showToast(R.string.formula_editor_parse_fail_formula_too_long);
					break;
			}
			return false;
		}

	}

	public void showToast(int ressourceId) {
		Utils.displayToast((SherlockFragmentActivity) context, context.getResources().getString(ressourceId));
		//		Toast.makeText(context, getString(ressourceId), Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onKey(View view, int keyCode, KeyEvent event) {
		Log.i("info", "onKey() in FE-Fragment! keyCode: " + keyCode);
		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				confirmBackTimeStamp[0] = confirmBackTimeStamp[1];
				confirmBackTimeStamp[1] = System.currentTimeMillis();
				confirmBackCounter++;
				endFormulaEditor();
				return true;
		}

		return false;
	}

	public void endFormulaEditor() {

		if (formulaEditorEditText.hasChanges()) {
			if (saveFormulaIfPossible()) {
				onUserDismiss();
			}
		} else {
			onUserDismiss();
		}
	}

	public void refreshFormulaPreviewString(String formulaString) {
		currentFormula.refreshTextField(brickView, formulaEditorEditText.getText().toString());

	}

}
