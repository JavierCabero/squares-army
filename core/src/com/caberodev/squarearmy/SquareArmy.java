package com.caberodev.squarearmy;

import com.badlogic.gdx.ApplicationAdapter;
import com.caberodev.squarearmy.main.Boot;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 * Copyright 2015 (c) All Rights Reserved.
 *                                          
 */
public class SquareArmy extends ApplicationAdapter {

	@Override
	public void create() {
		// Start Engines
		DrawEngine .start();
		LogicEngine.start();
		InputEngine.start();
		
		// Start game
		new Boot();
	}
	
	@Override
	public void render() { // Main loop
		
		// Update
		LogicEngine.update();
		
		// Draw 
		DrawEngine.draw();
	}
}
