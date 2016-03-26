package com.caberodev.squarearmy;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.caberodev.squarearmy.util.DataDictionary;

/**
 * 
 * @author Javier Cabero Guerra 
 * 
 */
public class HeroCamera {
	
	private static float camera_smooth = 32f;
	private static Vec2  last_camera_pos;
	
	public HeroCamera() {
		
		last_camera_pos = new Vec2(Display.getWidth()/2, Display.getHeight()/2);
	}
	
	public void followHeroPlayer() { 
		
		float dx = DataDictionary.global._float("playerX") - last_camera_pos.x;
		float dy = DataDictionary.global._float("playerY") - last_camera_pos.y;
		
		//System.out.println("Player (" + DataDictionary.global._float("playerX") + ", " + DataDictionary.global._float("playerY") + "),  Camera (" + last_camera_pos.x + ", " + last_camera_pos.y + ")  Difference (" + dx + ", " + dy + ")");
		
		dx /= camera_smooth;
		dy /= camera_smooth;

		float camera_x = last_camera_pos.x + dx;
		float camera_y = last_camera_pos.y + dy;
		
		last_camera_pos.x = camera_x;
		last_camera_pos.y = camera_y;
		
//		System.out.println("Smooth difference (" + dx + ", " + dy + ")");
		GL11.glTranslatef(-dx, -dy, 0);
	}
}
