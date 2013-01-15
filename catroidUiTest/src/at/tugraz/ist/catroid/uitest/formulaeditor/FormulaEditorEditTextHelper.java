package at.tugraz.ist.catroid.uitest.formulaeditor;

import android.widget.EditText;

import com.jayway.android.robotium.solo.Solo;

public class FormulaEditorEditTextHelper {

	private EditText formulaEditorEditText;
	//	public HashMap<String, Integer> characterMap = null;

	private int optionBarHeight;
	private int brickHeight;
	private int actionbarHeight;

	private float oneCharacterWidthApproximation;
	private float threeCharactersWidthApproximation;
	private int lineHeight;
	private int visibleLinesInEditTextfield;
	private int totalLinesForTheInput;
	private float firstLineYCoordinate;
	private float firstCharacterInEditTextFieldOffset;

	private int heightOfViewInPixels;
	private int widthOfViewInPixels;

	public FormulaEditorEditTextHelper(EditText formulaEditorEditText, Solo solo) {

		this.formulaEditorEditText = formulaEditorEditText;

		// TODO: adapt variables according to device.
		// Old hardcoded variables for smaller device (800 x 480)
		//		optionBarHeight = 50;// should be shorter on the nexus S
		//		brickHeight = 99;
		//		actionbarHeight = 120;
		//		oneCharacterWidthApproximation = 16;
		//		threeCharactersWidthApproximation = oneCharacterWidthApproximation * 3;
		//		lineHeight = 41;
		//		visibleLinesInEditTextfield = 7;
		//		totalLinesForTheInput = 14;
		//		firstLineYCoordinate = brickHeight + actionbarHeight + optionBarHeight;
		//		firstCharacterInEditTextFieldOffset = 8;

		optionBarHeight = 50;
		brickHeight = 141;
		actionbarHeight = 132;
		//		actionbarHeight = solo.getActivityMonitor().getLastActivity().getActionBar().getHeight();

		oneCharacterWidthApproximation = 23.9f;
		threeCharactersWidthApproximation = oneCharacterWidthApproximation * 3;

		lineHeight = 58;
		//		lineHeight = formulaEditorEditText.getLineHeight();
		visibleLinesInEditTextfield = 10;
		totalLinesForTheInput = 14;
		firstLineYCoordinate = brickHeight + actionbarHeight + optionBarHeight;
		firstCharacterInEditTextFieldOffset = 11f;

		heightOfViewInPixels = formulaEditorEditText.getHeight();
		widthOfViewInPixels = formulaEditorEditText.getWidth();

	}

	//	public void createCharacterMap() {

	//		characterMap = new HashMap<String, Integer>();
	//		characterMap.put("a", new Integer(26));
	//
	//	}

	public float getOneCharacterWidthApproximation() {
		return oneCharacterWidthApproximation;
	}

	public float getThreeCharactersWidthApproximation() {
		return threeCharactersWidthApproximation;
	}

	public int getLineHeight() {
		return lineHeight;
	}

	public int getVisibleLinesInEditTextfield() {
		return visibleLinesInEditTextfield;
	}

	public int getTotalLinesForTheInput() {
		return totalLinesForTheInput;
	}

	public float getFirstLineYCoordinate() {
		return firstLineYCoordinate;
	}

	public float getFirstCharacterInEditTextFieldOffset() {
		return firstCharacterInEditTextFieldOffset;
	}

	public int getHeightOfViewInPixels() {
		return heightOfViewInPixels;
	}

	public int getWidthOfViewInPixels() {
		return widthOfViewInPixels;
	}
}
