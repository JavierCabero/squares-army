package main;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;
import org.lwjgl.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import appearance.MiddleScreenTexture;

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

	/* World */
	private World world;
	private int state;
	private MiddleScreenTexture gameOver;
	private String gameOverTexture = "gameover";

	/* Constructor: Every initialization is here */
	public Boot() {

		/* Display and OpenGL */
		setUpScreen();

		gameOver = new MiddleScreenTexture(loadTexture(gameOverTexture));
		glDisable(GL_TEXTURE_2D);
		
		state = 1;
		world = new World(this, INITIAL_MINIONS);

		while (running) {
			// Render

			glClear(GL_COLOR_BUFFER_BIT);

			/* Render Game Over */
			if (state == 1) {
				world.update();
				world.screenAroundPlayer();
				world.render();
			} else {
				/* Game Over */
				keyboardInput();
				gameOver.render();
			}
			Display.update();
			Display.sync(60);

			if (Display.isCloseRequested()) {
				running = false;
			}
		}

		Display.destroy();
	}

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

	private void setUpScreen() {
		try {
			Display.setDisplayMode(new DisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT));
			Display.setTitle("Square armys");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		// Initialization code OpenGL
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, SCREEN_WIDTH, 0, SCREEN_HEIGHT, 1, -1);

		glMatrixMode(GL_MODELVIEW);
		
	
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public boolean getRunning() {
		return running;
	}

	private Texture loadTexture(String key) {
		try {
			return TextureLoader.getTexture("PNG", new FileInputStream(new File("res/" + key + ".png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		new Boot();
	}

}
