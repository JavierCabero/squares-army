package com.caberodev.squarearmy;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDisable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import com.caberodev.squarearmy.appearance.MiddleScreenTexture;
import com.caberodev.squarearmy.util.DataDictionary;
import com.caberodev.squarearmy.world.LivingFactory;
import com.caberodev.squarearmy.world.World;

/**
 *  Main function
 */
public class SquareArmy {

	private final int INITIAL_MINIONS = 50;
	private boolean running = true;

	/* World */
	private World world;
	private int state;
	private MiddleScreenTexture gameOver;
	
	public SquareArmy() {
		
		// Start Engines
		DrawEngine.start();
		LogicEngine.start();
		InputEngine.start();

		// Start game
		new World();
		//new MinionGenerator();
		LivingFactory.create("player", new DataDictionary());
		
		glDisable(GL_TEXTURE_2D);
		
		state = 1;
		world = new World();
		
		while (running) {
			// Render

			glClear(GL_COLOR_BUFFER_BIT);

			InputEngine.update();
			LogicEngine.update();
			DrawEngine.draw();

			/*
			float x=20,y=20,size=200;
			glColor4f(1,1,1,1);
			glBegin(GL_QUADS);
				glVertex2f(x, y); 
				glVertex2f(x, y + size); 
				glVertex2f(x + size, y + size); 
				glVertex2f(x + size, y); 
			glEnd();
			*/
			
			Display.update();
			Display.sync(60);

			if (Display.isCloseRequested()) {
				running = false;
			}
		}

		Display.destroy();
	}

	public void render() { // Main loop

		// Update
		LogicEngine.update();

		// Draw
		DrawEngine.draw();
	}

	public void setState(int state) {
		this.state = state;
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
		new SquareArmy();
	}
}
