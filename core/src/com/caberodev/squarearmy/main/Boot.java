package com.caberodev.squarearmy.main;


/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 * Copyright 2015 (c) All Rights Reserved.
 *                                          
 */
public class Boot {

	public static final int SCREEN_WIDTH  = 800;
	public static final int SCREEN_HEIGHT = 600;
	
	private final int INITIAL_MINIONS = 50;
	private boolean running = true;

	// World 
//	private World world;
//	private int state;
//	private MiddleScreenTexture gameOver;
//	private String gameOverTexture = "gameover";
	
	
	/* Constructor: Every initialization is here */
	public Boot() {

		//gameOver = new MiddleScreenTexture(loadTexture(gameOverTexture));
		//GL20.glDisable(GL20.GL_TEXTURE_2D);
		
		//state = 1;
		
		// World adds itself to Draw and Think lists
		new World(this, INITIAL_MINIONS);
		
		/*
			// Render Game Over 
			if (state == 1) {
				world.screenAroundPlayer();
			} else {
				// Game Over 
				keyboardInput();
				gameOver.render();
			}
		*/
	}

//	public void setState(int state) {
//		this.state = state;
//	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}

	public boolean getRunning() {
		return running;
	}
}
