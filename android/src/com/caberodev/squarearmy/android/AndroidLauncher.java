package com.caberodev.squarearmy.android;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.caberodev.squarearmy.InputEngine;
import com.caberodev.squarearmy.SquareArmy;
import com.caberodev.squarearmy.Vec2;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 * Copyright 2015 (c) All Rights Reserved.
 *                                          
 */
public class AndroidLauncher extends AndroidApplication implements SensorEventListener {
	
    private SensorManager mSensorManager;
    private Sensor accelerometer;
    
    private SquareArmy core;
    
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Initialize Game
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		core = new SquareArmy();
		initialize(core, config);

		// Initialize App functions
		initSensors(); 
        initListeners();
	}
	
	public void initSensors() {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

	}
    public void initListeners()
    {
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }
    
    @Override
    public void onDestroy()
    {
        mSensorManager.unregisterListener(this);
        super.onDestroy();
    }
 
    @Override
    public void onBackPressed()
    {
        mSensorManager.unregisterListener(this);
        super.onBackPressed();
    }
 
    @Override
    public void onResume()
    {
        initListeners();
        super.onResume();
    }
 
    @Override
    protected void onPause()
    {
        mSensorManager.unregisterListener(this);
        super.onPause();
    }   
 
    float[] inclineGravity = new float[3];
    float[] mAccel = {0, 0};
    private float epsilon = 0.1f;
    float pitch;
    float roll;
    
    long message_count = 0;
    
    @Override
    public void onSensorChanged(SensorEvent event) {
        //If type is accelerometer only assign values to global property mGravity
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            mAccel = event.values;
            
            // Send movement data to Input Layer
            float x = (mAccel[0] + event.values[0]) / 2f;
            float y = (mAccel[1] + event.values[1]) / 2f;
            
            // Normalize
            x /= 10;
            y /= 10;

            if(Math.abs(x) < epsilon) x = 0;
            if(Math.abs(y) < epsilon) y = 0;
            
            // Update movement direction
            InputEngine.dir = new Vec2(y, -x);
            
            // Save marks
            mAccel = new float[]{x, y};
        }  
    }
    
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Nothing
    }
}
