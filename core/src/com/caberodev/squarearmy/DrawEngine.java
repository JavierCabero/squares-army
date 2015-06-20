package com.caberodev.squarearmy;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 * Copyright 2015 (c) All Rights Reserved.
 *                                          
 */
public class DrawEngine {
	
	// Drawing helper
	public static SpriteBatch batch;
	
	// Default font for 2D text
	public static BitmapFont font;
	
	// List for drawables
	private static ArrayList<Drawable> drawList;
	
	// Background color
	public static Vec4 bgColor = new Vec4(0.14f, 0.2f, 0.14f, 1);
	
	public static void addDrawable(Drawable item) {
		drawList.add(item);
	}
	
	public static void delDrawable(Drawable item) {
		drawList.remove(item);
	}

	public static void start() {
		
		// Default SpriteBatch
		batch = new SpriteBatch();
		
		// Default font is Calibri
		font = new BitmapFont(Gdx.files.internal("data/calibri.fnt"),
				 Gdx.files.internal("data/calibri.png"), 
				 false);
		
		drawList = new ArrayList<Drawable>();
	}
	
	public static void draw() {
		
		// Clear and set background tint
		Gdx.gl.glClearColor(bgColor.x, bgColor.y, bgColor.z, bgColor.w);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Start drawing
		batch.begin();
		
		for(Drawable item : drawList) 
			item.draw();
		
		// End drawing
		batch.end();
	}
}
