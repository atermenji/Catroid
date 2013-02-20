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
package at.tugraz.ist.catroid.formulaeditor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import at.tugraz.ist.catroid.ProjectManager;
import at.tugraz.ist.catroid.content.Costume;

public class SensorHandler implements SensorEventListener {
	private static SensorHandler instance = null;
	private static android.hardware.SensorManager mySensorManager = null;
	private static Sensor mAccelerometer = null;
	private static Sensor mRotationVector = null;
	private static float[] rotationMatrix = new float[16];
	private static float[] rotationVector = new float[3];
	private static float radianToDegreeConst = 180f / (float) Math.PI;

	private static float linearAcceleartionX = 0f;
	private static float linearAcceleartionY = 0f;
	private static float linearAcceleartionZ = 0f;

	private SensorHandler(Context context) {
		mySensorManager = (android.hardware.SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mySensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		mRotationVector = mySensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
	}

	public static void startSensorListener(Context context) {

		if (instance == null) {
			instance = new SensorHandler(context);
		}
		mySensorManager.unregisterListener(instance);
		mySensorManager.registerListener(instance, mAccelerometer, android.hardware.SensorManager.SENSOR_DELAY_NORMAL);
		mySensorManager.registerListener(instance, mRotationVector, android.hardware.SensorManager.SENSOR_DELAY_NORMAL);
	}

	public static void registerListener(SensorEventListener listener) {
		if (instance == null) {
			return;
		}
		mySensorManager.registerListener(listener, mAccelerometer, android.hardware.SensorManager.SENSOR_DELAY_NORMAL);
		mySensorManager.registerListener(listener, mRotationVector, android.hardware.SensorManager.SENSOR_DELAY_NORMAL);
	}

	public static void unregisterListener(SensorEventListener listener) {
		if (instance == null) {
			return;
		}
		mySensorManager.unregisterListener(listener);
	}

	public static void stopSensorListeners() {
		if (instance == null) {
			return;
		}
		mySensorManager.unregisterListener(instance);
	}

	public static Double getSensorValue(String sensorName) {
		Double sensorValue = 0.0;
		if (sensorName.equals(Sensors.X_ACCELERATION_.sensorName)) {

			sensorValue = Double.valueOf(linearAcceleartionX);
		}
		if (sensorName.equals(Sensors.Y_ACCELERATION_.sensorName)) {
			sensorValue = Double.valueOf(linearAcceleartionY);
		}
		if (sensorName.equals(Sensors.Z_ACCELERATION_.sensorName)) {
			sensorValue = Double.valueOf(linearAcceleartionZ);
		}
		if (sensorName.equals(Sensors.Z_ORIENTATION_.sensorName)) {
			if (mySensorManager == null) {
				return 0d;
			}

			float[] orientations = new float[3];
			getRotationMatrixFromVector(rotationMatrix, rotationVector);
			SensorManager.getOrientation(rotationMatrix, orientations);
			sensorValue = Double.valueOf(orientations[0]);
			sensorValue *= radianToDegreeConst;
			Log.e("info", "Z-Orientierung: " + sensorValue);

		}
		if (sensorName.equals(Sensors.X_ORIENTATION_.sensorName)) {
			if (mySensorManager == null) {
				return 0d;
			}
			float[] orientations = new float[3];
			getRotationMatrixFromVector(rotationMatrix, rotationVector);
			SensorManager.getOrientation(rotationMatrix, orientations);
			sensorValue = Double.valueOf(orientations[1]);
			sensorValue *= radianToDegreeConst;
			Log.e("info", "X-Orientierung: " + sensorValue);
		}
		if (sensorName.equals(Sensors.Y_ORIENTATION_.sensorName)) {
			if (mySensorManager == null) {
				return 0d;
			}
			float[] orientations = new float[3];
			getRotationMatrixFromVector(rotationMatrix, rotationVector);
			SensorManager.getOrientation(rotationMatrix, orientations);
			sensorValue = Double.valueOf(orientations[2]);
			sensorValue *= radianToDegreeConst;
			Log.e("info", "Y-Orientierung: " + sensorValue);
		}
		//Look VALUES

		if (getCurrentObjectLook() == null) {
			return 0d;
		}

		if (sensorName.equals(Sensors.LOOK_X_.sensorName)) {
			sensorValue = Double.valueOf(getCurrentObjectLook().getXPosition());
		}
		if (sensorName.equals(Sensors.LOOK_Y_.sensorName)) {
			sensorValue = Double.valueOf(getCurrentObjectLook().getYPosition());
		}
		if (sensorName.equals(Sensors.LOOK_GHOSTEFFECT_.sensorName)) {
			sensorValue = Double.valueOf(getCurrentObjectLook().getAlphaValue());
		}
		if (sensorName.equals(Sensors.LOOK_BRIGHTNESS_.sensorName)) {
			sensorValue = Double.valueOf(getCurrentObjectLook().getBrightnessValue());
		}
		if (sensorName.equals(Sensors.LOOK_SIZE_.sensorName)) {
			sensorValue = Double.valueOf(getCurrentObjectLook().scaleX);
		}
		if (sensorName.equals(Sensors.LOOK_ROTATION_.sensorName)) {
			sensorValue = Double.valueOf(getCurrentObjectLook().rotation);
		}
		if (sensorName.equals(Sensors.LOOK_LAYER_.sensorName)) {
			sensorValue = Double.valueOf(getCurrentObjectLook().zPosition);
		}

		return sensorValue;
	}

	private static Costume getCurrentObjectLook() {
		if (ProjectManager.getInstance().getCurrentSprite() == null) {
			return null;
		}
		return ProjectManager.getInstance().getCurrentSprite().costume;
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		switch (event.sensor.getType()) {
			case Sensor.TYPE_LINEAR_ACCELERATION:
				linearAcceleartionX = event.values[0];
				linearAcceleartionY = event.values[1];
				linearAcceleartionZ = event.values[2];
				break;
			case Sensor.TYPE_ROTATION_VECTOR:
				rotationVector[0] = event.values[0];
				rotationVector[1] = event.values[1];
				rotationVector[2] = event.values[2];
				break;
		}

	}

	//For API Level < 9
	public static void getRotationMatrixFromVector(float[] R, float[] rotationVector) {

		float q0;
		float q1 = rotationVector[0];
		float q2 = rotationVector[1];
		float q3 = rotationVector[2];

		q0 = 1 - q1 * q1 - q2 * q2 - q3 * q3;
		q0 = (q0 > 0) ? (float) Math.sqrt(q0) : 0;

		float sq_q1 = 2 * q1 * q1;
		float sq_q2 = 2 * q2 * q2;
		float sq_q3 = 2 * q3 * q3;
		float q1_q2 = 2 * q1 * q2;
		float q3_q0 = 2 * q3 * q0;
		float q1_q3 = 2 * q1 * q3;
		float q2_q0 = 2 * q2 * q0;
		float q2_q3 = 2 * q2 * q3;
		float q1_q0 = 2 * q1 * q0;

		R[0] = 1 - sq_q2 - sq_q3;
		R[1] = q1_q2 - q3_q0;
		R[2] = q1_q3 + q2_q0;
		R[3] = 0.0f;

		R[4] = q1_q2 + q3_q0;
		R[5] = 1 - sq_q1 - sq_q3;
		R[6] = q2_q3 - q1_q0;
		R[7] = 0.0f;

		R[8] = q1_q3 - q2_q0;
		R[9] = q2_q3 + q1_q0;
		R[10] = 1 - sq_q1 - sq_q2;
		R[11] = 0.0f;

		R[12] = R[13] = R[14] = 0.0f;
		R[15] = 1.0f;

	}
}
