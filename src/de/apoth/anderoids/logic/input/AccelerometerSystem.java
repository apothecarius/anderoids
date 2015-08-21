package de.apoth.anderoids.logic.input;

import java.util.List;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AccelerometerSystem implements SensorEventListener{
	private static boolean accelIsSupported = false;
	private static boolean systemRunning = false;
	//private SensorManager sensorManager = null;
	
	//TODO even out the angleBuffer with the previous value (and write to the previous value)
	//so that jittering of the sensor is countered
	float[] prevAngleBuffer;
	float[] angleBuffer; 
	
	
	/**
	 * the device is checked whether an accelerometer is present.
	 * 
	 * Instancing an object before having called this function results in a crash
	 * 
	 * This must be done beforehand, because a constructor cant return a boolean
	 * @param ctx
	 * @return
	 */
	public static boolean checkForAccelerometer(Context ctx)
	{
		assert(ctx != null);
		SensorManager sensorManager = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if(sensors.size() >0)
        	accelIsSupported = true;
        else
        	accelIsSupported = false;
        return accelIsSupported;
	}
	
	public AccelerometerSystem(Context ctx)
	{
		assert(accelIsSupported);
		this.angleBuffer = new float[3];
		this.prevAngleBuffer = new float[3];
		for (int i = 0; i < angleBuffer.length; i++) {
			angleBuffer[i] = 0.0f;
			prevAngleBuffer[i] = 0.0f;
		}
		
		SensorManager sensorManager = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        assert(sensors.size() > 0);
		Sensor sensor = sensors.get(0);
		int tries = 3;
		while((! systemRunning ) && tries != 0)
		{
			systemRunning = sensorManager.registerListener(this, sensor,SensorManager.SENSOR_DELAY_GAME);
			tries++;
		}
		
		if(! systemRunning)
		{
			accelIsSupported = false;
			return;
		}
	}
	
	public void deactivateSystem(Context ctx)
	{
        assert(systemRunning && accelIsSupported);
        SensorManager sensorManager = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.unregisterListener(this);
        
        systemRunning = false;
    }
 
	
	public synchronized float[] getDeviceAngle() {
		return angleBuffer;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// do nothing
	}

	@Override
	public synchronized void onSensorChanged(SensorEvent event) {
		for (int i = 0; i < angleBuffer.length; i++) {
			this.angleBuffer[i] = event.values[i];	
		}
		
		
	}
}
