package com.caberodev.squarearmy.core;

import org.lwjgl.input.Keyboard;

import com.caberodev.squarearmy.Vec2;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 * Abstract the meaning of keys.
 *                                          
 */
public class InputEngine {

	// Used for player movement
	public static Vec2 dir = new Vec2(0, 0);
	
	public static void start() {
		// Nothing
	}
	
	public static void update() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			dir.y = 1;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			dir.y = -1;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			dir.x = -1;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			dir.x = 1;
		} else {
			dir.x = 0;
			dir.y = 0;
		}
	}
}
