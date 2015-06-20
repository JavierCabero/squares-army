package com.caberodev.squarearmy.main;


/*
 * Game Core is a java project made to learn how to "fight"
 * against the big amount of work you have to put in a game
 * development. This project is going to be almost full
 * commented and with every line of code making sense.
 * 
 */
public class Boot {

	/* Constant: Where essential data is */
	public final static int SCREEN_WIDTH = 800;
	public final static int SCREEN_HEIGHT = 600;

	private final int INITIAL_MINIONS = 50;
	private boolean running = true;

	/* World 
	private World world;
	private int state;
	private MiddleScreenTexture gameOver;
	private String gameOverTexture = "gameover";
	*/
	
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

	/*
	private void keyboardInput() {
		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
			world = new World(this, INITIAL_MINIONS);
			state = 1;
			glDisable(GL_TEXTURE_2D);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			setRunning(false);
		}
	}
	
	
	public void setState(int state) {
		this.state = state;
	}
*/
	public void setRunning(boolean running) {
		this.running = running;
	}

	public boolean getRunning() {
		return running;
	}
}
