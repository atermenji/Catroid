///**
// *  Catroid: An on-device graphical programming language for Android devices
// *  Copyright (C) 2010-2011 The Catroid Team
// *  (<http://code.google.com/p/catroid/wiki/Credits>)
// *  
// *  This program is free software: you can redistribute it and/or modify
// *  it under the terms of the GNU Affero General Public License as
// *  published by the Free Software Foundation, either version 3 of the
// *  License, or (at your option) any later version.
// *  
// *  An additional term exception under section 7 of the GNU Affero
// *  General Public License, version 3, is available at
// *  http://www.catroid.org/catroid_license_additional_term
// *  
// *  This program is distributed in the hope that it will be useful,
// *  but WITHOUT ANY WARRANTY; without even the implied warranty of
// *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// *  GNU Affero General Public License for more details.
// *   
// *  You should have received a copy of the GNU Affero General Public License
// *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
// */
//package at.tugraz.ist.catroid.formulaeditor;
//
//import java.util.LinkedList;
//import java.util.List;
//
//import android.view.KeyEvent;
//
//public class CatKeyEvent extends KeyEvent {
//
//	/* FUNCTIONS */
//	public static final int KEYCODE_SIN = 1000;
//	public static final int KEYCODE_COS = 1001;
//	public static final int KEYCODE_TAN = 1002;
//	public static final int KEYCODE_LN = 1003;
//	public static final int KEYCODE_LOG = 1004;
//	public static final int KEYCODE_PI = 1005;
//	public static final int KEYCODE_SQUAREROOT = 1006;
//	public static final int KEYCODE_EULER = 1007;
//	public static final int KEYCODE_RANDOM = 1008;
//	public static final int KEYCODE_ABS = 1009;
//	public static final int KEYCODE_ROUND = 1010;
//
//	/* SENSOR */
//	public static final int KEYCODE_SENSOR_BUTTON = 1100;
//	public static final int KEYCODE_SENSOR1 = 1101;
//	public static final int KEYCODE_SENSOR2 = 1102;
//	public static final int KEYCODE_SENSOR3 = 1103;
//	public static final int KEYCODE_SENSOR4 = 1104;
//	public static final int KEYCODE_SENSOR5 = 1105;
//	public static final int KEYCODE_SENSOR6 = 1106;
//	public static final int KEYCODE_SENSOR7 = 1107;
//
//	/* LOOK + Backets */
//	public static final int KEYCODE_BRACKET = 1200;
//	public static final int KEYCODE_LOOK_BUTTON = 1201;
//	public static final int KEYCODE_LOOK_X = 1202;
//	public static final int KEYCODE_LOOK_Y = 1203;
//	public static final int KEYCODE_LOOK_GHOSTEFFECT = 1204;
//	public static final int KEYCODE_LOOK_BRIGHTNESS = 1205;
//	public static final int KEYCODE_LOOK_SIZE = 1206;
//	public static final int KEYCODE_LOOK_ROTATION = 1207;
//	public static final int KEYCODE_LOOK_LAYER = 1208;
//
//	/* OPERATORS */
//	public static final int KEYCODE_NOT_EQUAL = 1300;
//	public static final int KEYCODE_LESSER_THAN = 1301;
//	public static final int KEYCODE_GREATER_THAN = 1302;
//	public static final int KEYCODE_LOGICAL_AND = 1303;
//	public static final int KEYCODE_LOGICAL_OR = 1304;
//	//	public static final int KEYCODE_EQUAL = 1305 // TODO
//
//	public static final int KEYCODE_OBJECT_BUTTON = 1400;
//	public static final int KEYCODE_MATH_BUTTON = 1500;
//	public static final int KEYCODE_LOGIC_BUTTON = 1600;
//	public static final int KEYCODE_VARIABLES_BUTTON = 1700;
//	public static final int KEYCODE_UNDO = 1800;
//	public static final int KEYCODE_REDO = 1900;
//	public static final int KEYCODE_COMPUTE = 2000;
//
//	/* USER VARIABLE */
//	public static final int KEYCODE_USER_VARIABLE = 2100;
//	private String userVariableName = null;
//
//	// Please update the functions of this class if you add new KEY_CODE constants ^_^
//
//	public CatKeyEvent(KeyEvent origEvent) {
//		super(origEvent);
//	}
//
//	public CatKeyEvent(int action, int code) {
//		super(action, code);
//	}
//
//	public CatKeyEvent(String userVariableName) {
//		super(KeyEvent.ACTION_DOWN, KEYCODE_USER_VARIABLE);
//		this.userVariableName = userVariableName;
//	}
//

//
//}
