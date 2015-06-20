package com.caberodev.squarearmy;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.caberodev.squarearmy.main.Boot;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 * Copyright 2015 (c) All Rights Reserved.
 *                                          
 */
public class SquareArmy extends ApplicationAdapter {

    public static Text2D accelValuesText2D;
    
	@Override
	public void create() {
		// Start Engines
		DrawEngine .start();
		LogicEngine.start();
		InputEngine.start();
		
		// Start game
		new Boot();
		
		// Load text
		accelValuesText2D = new Text2DAccelerometerSensible(DrawEngine.batch, DrawEngine.font, Gdx.graphics.getWidth()>>1 - 1, Gdx.graphics.getHeight()>>1);
	}
	
	@Override
	public void render() { // Main loop
		
		// Update
		LogicEngine.update();
		
		// Draw 
		DrawEngine.draw();
	}
}
