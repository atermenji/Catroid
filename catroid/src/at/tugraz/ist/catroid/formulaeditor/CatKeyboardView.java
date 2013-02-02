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
/*
 * Copyright (C) 2008-2009 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package at.tugraz.ist.catroid.formulaeditor;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import at.tugraz.ist.catroid.R;
import at.tugraz.ist.catroid.ui.fragment.FormulaEditorListFragment;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class CatKeyboardView extends KeyboardView implements KeyboardView.OnKeyboardActionListener {

	private FormulaEditorEditText mFormulaEditorEditText;
	private Keyboard mFormulaEditorKeyboard;
	private Context mContext;

	public CatKeyboardView(Context context, AttributeSet attrs) {

		super(context, attrs);
		mContext = context;
		setOnKeyboardActionListener(this);
		mFormulaEditorEditText = null;
		mFormulaEditorKeyboard = null;

		mFormulaEditorKeyboard = new Keyboard(this.getContext(), R.xml.formula_editor_keyboard);
		setKeyboard(mFormulaEditorKeyboard);

		//		LayoutParams relative = new LayoutParams(source);
		//		this.symbols.setShifted(false);
		//		this.symbols_shifted.setShifted(true);
		//		this.setBackgroundColor(0xFF6103);
		//		this.awakenScrollBars();
		//
		//		ArrayList<Key> keys = (ArrayList<Key>) this.symbols.getKeys();
		//
		//				for (int i = 0; i < keys.size(); i++) {
		//					Key key = keys.get(i);
		//					key.iconPreview = key.icon;
		//					key.popupCharacters = key.label;
		//				}

		//    public CatKeyboardView(Context context, AttributeSet attrs, int defStyle) {
		//        super(context, attrs, defStyle);

		Log.i("info", "CatKeyboardView()-Constructor");
	}

	@Override
	public void onKey(int primaryCode, int[] keyCodes) {

		CatKeyEvent catKeyEvent = null;

		switch (primaryCode) {
			case KeyEvent.KEYCODE_MENU:
				// TODO: Do we need this KeyEvent ? :O
				break;
			case KeyEvent.KEYCODE_EQUALS:
				//TODO implement 
				break;
			case CatKeyEvent.KEYCODE_COMPUTE:
				//TODO implement functionality (interpret) and output dialog ( Issue 8.64c)
				break;
			case CatKeyEvent.KEYCODE_UNDO:
				mFormulaEditorEditText.undo();
				break;
			case CatKeyEvent.KEYCODE_REDO:
				mFormulaEditorEditText.redo();
				break;
			case CatKeyEvent.KEYCODE_MATH_BUTTON:
				showFormulaEditorListFragment(FormulaEditorListFragment.MATH_TAG, R.string.formula_editor_math,
						CatKeyEvent.KEYCODE_SIN);
				break;
			case CatKeyEvent.KEYCODE_LOGIC_BUTTON:
				showFormulaEditorListFragment(FormulaEditorListFragment.LOGIC_TAG, R.string.formula_editor_logic,
						CatKeyEvent.KEYCODE_NOT_EQUAL);
				break;
			case CatKeyEvent.KEYCODE_OBJECT_BUTTON:
				showFormulaEditorListFragment(FormulaEditorListFragment.OBJECT_TAG,
						R.string.formula_editor_choose_look_variable, CatKeyEvent.KEYCODE_LOOK_X);
				break;
			case CatKeyEvent.KEYCODE_SENSOR_BUTTON:
				showFormulaEditorListFragment(FormulaEditorListFragment.SENSOR_TAG, R.string.formula_editor_sensors,
						CatKeyEvent.KEYCODE_SENSOR1);
				break;
			case CatKeyEvent.KEYCODE_VARIABLES_BUTTON:
				//TODO implement Fragment
				break;
			default:
				catKeyEvent = new CatKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, primaryCode));
				mFormulaEditorEditText.handleKeyEvent(catKeyEvent);
				break;
		}

	}

	@Override
	public void swipeLeft() {
	}

	@Override
	public void swipeRight() {
	}

	@Override
	public void swipeUp() {
	}

	@Override
	public void swipeDown() {
	}

	@Override
	public void onPress(int primaryCode) {
		//		Log.i("info", "CatKeybaordView.onPress(): " + primaryCode);

	}

	@Override
	public void onRelease(int primaryCode) {
		//		Log.i("info", "CatKeybaordView.onRelease(): " + primaryCode);

	}

	@Override
	public void onText(CharSequence text) {
		//		Log.i("info", "CatKeybaordView.onText(): ");

	}

	public void setFormulaEditText(FormulaEditorEditText formulaEditorEditText) {
		mFormulaEditorEditText = formulaEditorEditText;
	}

	private void showFormulaEditorListFragment(String tag, int actionbarResId, int keycodeOffset) {
		FragmentManager fragmentManager = ((SherlockFragmentActivity) mContext).getSupportFragmentManager();
		Fragment fragment = fragmentManager.findFragmentByTag(tag);

		if (fragment == null) {
			fragment = new FormulaEditorListFragment(mFormulaEditorEditText, keycodeOffset,
					mContext.getString(actionbarResId), tag);
		}
		((FormulaEditorListFragment) fragment).showFragment(mContext);
	}
}
