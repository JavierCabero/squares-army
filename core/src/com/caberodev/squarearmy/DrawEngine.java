package com.caberodev.squarearmy;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

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

	// Camera handler
	public static HeroCamera camera;
	
	// Default font for 2D text
	public static BitmapFont font;

	// For rendering shapes
	public static ShapeRenderer shapeRenderer;
	
	// List for drawables
	private static ArrayList<Drawable> drawList;
	private static ArrayList<Text2D> textList;
	
	// Background color
	public static Vec4 bgColor = new Vec4(0.58f, 0.58f, 0.7f, 1);
	
	public static void addDrawable(Drawable item) {
		drawList.add(item);
	}
	
	public static void delDrawable(Drawable item) {
		drawList.remove(item);
	}
	
	public static void addDrawable(Text2D item) {
		textList.add(item);
	}
	
	public static void delDrawable(Text2D item) {
		textList.remove(item);
	}
	 
	public static void start() {
		
		// Default SpriteBatch
		batch = new SpriteBatch();
		
		// Default font is Calibri
		font = new BitmapFont(Gdx.files.internal("data/calibri.fnt"),
				 			  Gdx.files.internal("data/calibri.png"), 
				 			  false);
		
		drawList = new ArrayList<Drawable>();
		textList = new ArrayList<Text2D>();
        camera   = new HeroCamera();
        shapeRenderer = new ShapeRenderer();
        
        Text2D text = new Text2D(batch, font, 15, Gdx.graphics.getHeight() - 15);
        text.setText("Dev Text | Screen (" 
        		+ Gdx.graphics.getWidth() + ", " 
        		+ Gdx.graphics.getHeight() 
        		+ ") Density: " + Gdx.graphics.getDensity());
        
	}
	
	public static void draw() {
		
		// Update game camera
		camera.followHeroPlayer(); 
		shapeRenderer.setProjectionMatrix(camera.combined);
		
		// Clear and set background tint
		Gdx.gl.glClearColor(bgColor.x, bgColor.y, bgColor.z, bgColor.w);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Shape drawing
		batch.begin();
		for(Drawable item : drawList) 
			item.draw();
		batch.end();
		
		// Text drawing
		batch.begin();
		for(Text2D item : textList)
			item.draw();
		batch.end();
		
	}
}
