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
package at.tugraz.ist.catroid.content.bricks;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import at.tugraz.ist.catroid.R;
import at.tugraz.ist.catroid.content.Script;
import at.tugraz.ist.catroid.content.Sprite;
import at.tugraz.ist.catroid.formulaeditor.Formula;
import at.tugraz.ist.catroid.ui.ScriptTabActivity;

public class RepeatBrick extends LoopBeginBrick implements OnClickListener {
	private static final long serialVersionUID = 1L;

	private Formula timesToRepeatFormula;

	public RepeatBrick(Sprite sprite, int timesToRepeat) {
		this.sprite = sprite;
		timesToRepeatFormula = new Formula(Integer.toString(timesToRepeat));
	}

	public RepeatBrick(Sprite sprite, Formula timesToRepeat) {
		this.sprite = sprite;
		timesToRepeatFormula = timesToRepeat;
	}

	@Override
	public int getRequiredResources() {
		return NO_RESOURCES;
	}

	@Override
	public void execute() {
		int timesToRepeat = timesToRepeatFormula.interpret().intValue();

		if (timesToRepeat <= 0) {
			Script script = loopEndBrick.getScript();
			script.setExecutingBrickIndex(script.getBrickList().indexOf(loopEndBrick));
			return;
		}
		loopEndBrick.setTimesToRepeat(timesToRepeat);
		super.setFirstStartTime();
	}

	@Override
	public Brick clone() {
		return new RepeatBrick(getSprite(), timesToRepeatFormula);
	}

	@Override
	public View getView(Context context, int brickId, BaseAdapter adapter) {

		View view = View.inflate(context, R.layout.brick_repeat, null);

		TextView text = (TextView) view.findViewById(R.id.brick_repeat_text_view);
		EditText edit = (EditText) view.findViewById(R.id.brick_repeat_edit_text);

		timesToRepeatFormula.setTextFieldId(R.id.brick_repeat_edit_text);
		timesToRepeatFormula.refreshTextField(view);

		text.setVisibility(View.GONE);
		edit.setVisibility(View.VISIBLE);

		edit.setOnClickListener(this);
		return view;
	}

	@Override
	public View getPrototypeView(Context context) {
		return View.inflate(context, R.layout.brick_repeat, null);
	}

	@Override
	public void onClick(View view) {
		ScriptTabActivity activity = null;
		if (view.getContext().getClass().equals(ScriptTabActivity.class)) {
			activity = (ScriptTabActivity) view.getContext();
		} else {
			activity = (ScriptTabActivity) ((ContextWrapper) view.getContext()).getBaseContext();
		}

		activity.showFormulaEditorDialog(this, timesToRepeatFormula);

	}

}
