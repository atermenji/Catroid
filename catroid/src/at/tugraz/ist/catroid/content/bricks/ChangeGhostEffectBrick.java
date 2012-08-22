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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import at.tugraz.ist.catroid.R;
import at.tugraz.ist.catroid.content.Sprite;
import at.tugraz.ist.catroid.formulaeditor.Formula;
import at.tugraz.ist.catroid.ui.fragment.FormulaEditorFragment;

public class ChangeGhostEffectBrick implements Brick, OnClickListener {
	private static final long serialVersionUID = 1L;
	private Sprite sprite;

	private transient View view;

	private Formula changeGhostEffect;

	private transient Brick instance = null;

	public ChangeGhostEffectBrick(Sprite sprite, double changeGhostEffectValue) {
		this.sprite = sprite;

		changeGhostEffect = new Formula(Double.toString(changeGhostEffectValue));
	}

	public ChangeGhostEffectBrick(Sprite sprite, Formula changeGhostEffect) {
		this.sprite = sprite;

		this.changeGhostEffect = changeGhostEffect;
	}

	@Override
	public int getRequiredResources() {
		return NO_RESOURCES;
	}

	@Override
	public void execute() {
		float changeGhostEffectValue = changeGhostEffect.interpretFloat() / -100.0F;
		sprite.costume.changeAlphaValueBy(changeGhostEffectValue);
	}

	@Override
	public Sprite getSprite() {
		return this.sprite;
	}

	@Override
	public View getView(Context context, int brickId, BaseAdapter adapter) {

		if (instance == null) {
			instance = this;
		}

		view = View.inflate(context, R.layout.brick_change_ghost_effect, null);

		TextView textX = (TextView) view.findViewById(R.id.brick_change_ghost_effect_text_view);
		EditText editX = (EditText) view.findViewById(R.id.brick_change_ghost_effect_edit_text);

		changeGhostEffect.setTextFieldId(R.id.brick_change_ghost_effect_edit_text);
		changeGhostEffect.refreshTextField(view);

		textX.setVisibility(View.GONE);
		editX.setVisibility(View.VISIBLE);
		editX.setOnClickListener(this);

		return view;
	}

	@Override
	public View getPrototypeView(Context context) {
		return View.inflate(context, R.layout.brick_change_ghost_effect, null);
	}

	@Override
	public Brick clone() {
		return new ChangeGhostEffectBrick(getSprite(), changeGhostEffect);
	}

	@Override
	public void onClick(View view) {
		FormulaEditorFragment.showFragment(view, this, changeGhostEffect);
	}
}
