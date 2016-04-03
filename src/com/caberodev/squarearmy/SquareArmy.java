package com.caberodev.squarearmy;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDisable;

import org.lwjgl.opengl.Display;

import com.caberodev.squarearmy.core.DrawEngine;
import com.caberodev.squarearmy.core.InputEngine;
import com.caberodev.squarearmy.core.LogicEngine;
import com.caberodev.squarearmy.world.World;

/**
 *  Main function
 */
public class SquareArmy {

	private boolean running = true;

	public SquareArmy() {
		
		// Start Engines
		DrawEngine.start();
		LogicEngine.start();
		InputEngine.start();

		// Start game
		new World();
		
		glDisable(GL_TEXTURE_2D);
		
		new World();
		
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

//	private Texture loadTexture(String key) {
//		try {
//			return TextureLoader.getTexture("PNG", new FileInputStream(new File("res/" + key + ".png")));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	public static void main(String[] args) {
		
		// main loop in constructor
		new SquareArmy();
	}
}
