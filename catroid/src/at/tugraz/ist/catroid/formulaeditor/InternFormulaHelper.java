package at.tugraz.ist.catroid.formulaeditor;

import java.util.LinkedList;
import java.util.List;

import at.tugraz.ist.catroid.R;

public class InternFormulaHelper {

	public List<InternToken> createInternTokensByCatKeyEvent(int resource, String userVariableName) {

		//USER VARIABLES
		if ((resource == 0) && (!(userVariableName.length() == 0))) {
			return buildUserVariable(userVariableName);
		}

		switch (resource) {
		// NUMBER:
			case R.id.formula_editor_keyboard_0:
				return buildNumber("0");
			case R.id.formula_editor_keyboard_1:
				return buildNumber("1");
			case R.id.formula_editor_keyboard_2:
				return buildNumber("2");
			case R.id.formula_editor_keyboard_3:
				return buildNumber("3");
			case R.id.formula_editor_keyboard_4:
				return buildNumber("4");
			case R.id.formula_editor_keyboard_5:
				return buildNumber("5");
			case R.id.formula_editor_keyboard_6:
				return buildNumber("6");
			case R.id.formula_editor_keyboard_7:
				return buildNumber("7");
			case R.id.formula_editor_keyboard_8:
				return buildNumber("8");
			case R.id.formula_editor_keyboard_9:
				return buildNumber("9");

				//FUNCTIONS:
			case R.id.formula_editor_keyboard_random:
				return buildDoubleParameterFunction(Functions.RAND, "0", "1");
			case R.string.formula_editor_function_sin:
				return buildSingleParameterFunction(Functions.SIN, "0");
			case R.string.formula_editor_function_cos:
				return buildSingleParameterFunction(Functions.COS, "0");
			case R.string.formula_editor_function_tan:
				return buildSingleParameterFunction(Functions.TAN, "0");
			case R.string.formula_editor_function_ln:
				return buildSingleParameterFunction(Functions.LN, "0");
			case R.string.formula_editor_function_log:
				return buildSingleParameterFunction(Functions.LOG, "0");
			case R.string.formula_editor_function_pi:
				return buildFunctionWithoutParametersAndBrackets(Functions.PI);
			case R.string.formula_editor_function_sqrt:
				return buildSingleParameterFunction(Functions.SQRT, "0");
			case R.string.formula_editor_function_e:
				return buildFunctionWithoutParametersAndBrackets(Functions.EULER);
			case R.string.formula_editor_function_rand:
				return buildDoubleParameterFunction(Functions.RAND, "0", "1");
			case R.string.formula_editor_function_abs:
				return buildSingleParameterFunction(Functions.ABS, "0");
			case R.string.formula_editor_function_round:
				return buildSingleParameterFunction(Functions.ROUND, "0");

				//SENSOR

			case R.string.formula_editor_sensor_x_acceleration:
				return buildSensor(Sensors.X_ACCELERATION_);
			case R.string.formula_editor_sensor_y_acceleration:
				return buildSensor(Sensors.Y_ACCELERATION_);
			case R.string.formula_editor_sensor_z_acceleration:
				return buildSensor(Sensors.Z_ACCELERATION_);
			case R.string.formula_editor_sensor_z_orientation:
				return buildSensor(Sensors.Z_ORIENTATION_);
			case R.string.formula_editor_sensor_x_orientation:
				return buildSensor(Sensors.X_ORIENTATION_);
			case R.string.formula_editor_sensor_y_orientation:
				return buildSensor(Sensors.Y_ORIENTATION_);

				//PERIOD
			case R.id.formula_editor_keyboard_decimal_mark:
				return buildPeriod();

				//OPERATOR

			case R.id.formula_editor_keyboard_plus:
				return buildOperator(Operators.PLUS);
			case R.id.formula_editor_keyboard_minus:
				return buildOperator(Operators.MINUS);
			case R.id.formula_editor_keyboard_mult:
				return buildOperator(Operators.MULT);
			case R.id.formula_editor_keyboard_divide:
				return buildOperator(Operators.DIVIDE);
			case R.string.formula_editor_operator_power:
				return buildOperator(Operators.POW);
			case R.id.formula_editor_keyboard_equal:
				return buildOperator(Operators.EQUAL);
			case R.string.formula_editor_logic_equal:
				return buildOperator(Operators.EQUAL);
			case R.string.formula_editor_logic_notequal:
				return buildOperator(Operators.NOT_EQUAL);
			case R.string.formula_editor_logic_lesserthan:
				return buildOperator(Operators.SMALLER_THAN);
			case R.string.formula_editor_logic_leserequal:
				return buildOperator(Operators.SMALLER_OR_EQUAL);
			case R.string.formula_editor_logic_greaterthan:
				return buildOperator(Operators.GREATER_THAN);
			case R.string.formula_editor_logic_greaterequal:
				return buildOperator(Operators.GREATER_OR_EQUAL);
			case R.string.formula_editor_logic_and:
				return buildOperator(Operators.LOGICAL_AND);
			case R.string.formula_editor_logic_or:
				return buildOperator(Operators.LOGICAL_OR);
			case R.string.formula_editor_logic_not:
				return buildOperator(Operators.NOT);

				//BRACKETS

			case R.id.formula_editor_keyboard_bracket_open:
				return buildBracketOpen();
			case R.id.formula_editor_keyboard_bracket_close:
				return buildBracketClose();

				//COSTUME

			case R.string.formula_editor_look_x:
				return buildLook(Sensors.LOOK_X_);
			case R.string.formula_editor_look_y:
				return buildLook(Sensors.LOOK_Y_);
			case R.string.formula_editor_look_ghosteffect:
				return buildLook(Sensors.LOOK_GHOSTEFFECT_);
			case R.string.formula_editor_look_brightness:
				return buildLook(Sensors.LOOK_BRIGHTNESS_);
			case R.string.formula_editor_look_size:
				return buildLook(Sensors.LOOK_SIZE_);
			case R.string.formula_editor_look_rotation:
				return buildLook(Sensors.LOOK_ROTATION_);
			case R.string.formula_editor_look_layer:
				return buildLook(Sensors.LOOK_LAYER_);

		}

		return null;

	}

	private List<InternToken> buildBracketOpen() {
		List<InternToken> returnList = new LinkedList<InternToken>();
		returnList.add(new InternToken(InternTokenType.BRACKET_OPEN));
		return returnList;
	}

	private List<InternToken> buildBracketClose() {
		List<InternToken> returnList = new LinkedList<InternToken>();
		returnList.add(new InternToken(InternTokenType.BRACKET_CLOSE));
		return returnList;
	}

	private List<InternToken> buildUserVariable(String userVariableName) {
		List<InternToken> returnList = new LinkedList<InternToken>();
		returnList.add(new InternToken(InternTokenType.USER_VARIABLE, userVariableName));
		return returnList;
	}

	private List<InternToken> buildPeriod() {
		List<InternToken> returnList = new LinkedList<InternToken>();
		returnList.add(new InternToken(InternTokenType.PERIOD));
		return returnList;
	}

	private List<InternToken> buildNumber(String numberValue) {
		List<InternToken> returnList = new LinkedList<InternToken>();
		returnList.add(new InternToken(InternTokenType.NUMBER, numberValue));
		return returnList;
	}

	private List<InternToken> buildLook(Sensors sensors) {
		List<InternToken> returnList = new LinkedList<InternToken>();
		returnList.add(new InternToken(InternTokenType.LOOK, sensors.sensorName));
		return returnList;
	}

	private List<InternToken> buildOperator(Operators operator) {
		List<InternToken> returnList = new LinkedList<InternToken>();
		returnList.add(new InternToken(InternTokenType.OPERATOR, operator.operatorName));
		return returnList;
	}

	private List<InternToken> buildSensor(Sensors sensor) {
		List<InternToken> returnList = new LinkedList<InternToken>();
		returnList.add(new InternToken(InternTokenType.SENSOR, sensor.sensorName));
		return returnList;
	}

	private List<InternToken> buildDoubleParameterFunction(Functions function, String firstParameterNumberValue,
			String secondParameterNumberValue) {

		List<InternToken> returnList = new LinkedList<InternToken>();
		returnList.add(new InternToken(InternTokenType.FUNCTION_NAME, function.functionName));

		returnList.add(new InternToken(InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN));

		returnList.add(new InternToken(InternTokenType.NUMBER, firstParameterNumberValue));

		returnList.add(new InternToken(InternTokenType.FUNCTION_PARAMETER_DELIMITER));

		returnList.add(new InternToken(InternTokenType.NUMBER, secondParameterNumberValue));

		returnList.add(new InternToken(InternTokenType.FUNCTION_PARAMETERS_BRACKET_CLOSE));

		return returnList;

	}

	private List<InternToken> buildSingleParameterFunction(Functions function, String parameterNumberValue) {

		List<InternToken> returnList = new LinkedList<InternToken>();
		returnList.add(new InternToken(InternTokenType.FUNCTION_NAME, function.functionName));
		returnList.add(new InternToken(InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN));
		returnList.add(new InternToken(InternTokenType.NUMBER, parameterNumberValue));
		returnList.add(new InternToken(InternTokenType.FUNCTION_PARAMETERS_BRACKET_CLOSE));
		return returnList;
	}

	private List<InternToken> buildFunctionWithoutParametersAndBrackets(Functions function) {

		List<InternToken> returnList = new LinkedList<InternToken>();
		returnList.add(new InternToken(InternTokenType.FUNCTION_NAME, function.functionName));
		return returnList;
	}

}
