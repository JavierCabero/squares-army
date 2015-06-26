package com.caberodev.squarearmy;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.caberodev.squarearmy.world.Hero;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 * Copyright 2015 (c) All Rights Reserved.
 *                                          
 */
public class HeroCamera extends OrthographicCamera {
	
	private static float camera_smooth = 32f;
	private static Vec2  last_camera_pos;
	
	public HeroCamera() {
		last_camera_pos = new Vec2(position.x, position.y);
	}
	
	public void followHeroPlayer() {
		
		// This line is need to make the 
		// camera use the right coordinate system.
		//
		// It must be before any camera data 
		// modification or the drawing won't 
		// work well.
		setToOrtho(false);
		
		float dx = Hero.player.x - last_camera_pos.x;
		float dy = Hero.player.y - last_camera_pos.y;
		
		dx /= camera_smooth;
		dy /= camera_smooth;

		position.x = last_camera_pos.x + dx;
		position.y = last_camera_pos.y + dy;
		
		last_camera_pos.x = position.x;
		last_camera_pos.y = position.y;
		
		update();
	}
}
