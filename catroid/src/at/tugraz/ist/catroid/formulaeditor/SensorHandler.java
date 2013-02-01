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
import at.tugraz.ist.catroid.ProjectManager;
import at.tugraz.ist.catroid.content.Costume;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class SensorHandler implements SensorEventListener {
	private static SensorHandler instance = null;
	private static Input sensors = null;
	private static android.hardware.SensorManager mySensorManager = null;
	private static Sensor mAccelerometer = null;

	private static float linearAcceleartionX = 0f;
	private static float linearAcceleartionY = 0f;
	private static float linearAcceleartionZ = 0f;

	private SensorHandler(Context context) {
		mySensorManager = (android.hardware.SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mySensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
	}

	public static void startSensorListener(Context context) {

		if (instance == null) {
			instance = new SensorHandler(context);
		}
		mySensorManager.unregisterListener(instance);
		mySensorManager.registerListener(instance, mAccelerometer, android.hardware.SensorManager.SENSOR_DELAY_NORMAL);

	}

	public static void stopSensorListeners() {
		if (instance == null) {
			return;
		}
		mySensorManager.unregisterListener(instance);
	}

	public static Double getSensorValue(String sensorName) {
		if (sensors == null) {
			sensors = Gdx.input;
		}
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
		if (sensorName.equals(Sensors.AZIMUTH_ORIENTATION_.sensorName)) {
			if (mySensorManager == null) {
				return 0d;
			}

			float[] orientations = new float[3];
			SensorManager.getOrientation(new float[3], orientations);
			sensorValue = Double.valueOf(orientations[0]);

			/*
			 * 
			 * values[0]: azimuth, rotation around the Z axis.
			 * values[1]: pitch, rotation around the X axis.
			 * values[2]: roll, rotation around the Y axis.
			 */

		}
		if (sensorName.equals(Sensors.PITCH_ORIENTATION_.sensorName)) {
			if (mySensorManager == null) {
				return 0d;
			}
			float[] orientations = new float[3];
			SensorManager.getOrientation(new float[3], orientations);
			sensorValue = Double.valueOf(orientations[1]);
		}
		if (sensorName.equals(Sensors.ROLL_ORIENTATION_.sensorName)) {
			if (mySensorManager == null) {
				return 0d;
			}
			float[] orientations = new float[3];
			SensorManager.getOrientation(new float[3], orientations);
			sensorValue = Double.valueOf(orientations[2]);
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

		sensors = null;
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
		}

	}
}
