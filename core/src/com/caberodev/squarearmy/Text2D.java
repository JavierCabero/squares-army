package com.caberodev.squarearmy;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 * Copyright 2015 (c) All Rights Reserved.
 *                                          
 */
public class Text2D implements Drawable {

	// Tools
	private SpriteBatch batch;
	private BitmapFont  font;
	
	// Text to be drawn
	private String text = "No Text";
	
	protected float x, y;
	
	public Text2D(SpriteBatch batch, BitmapFont font, float x, float y) {
		this.batch = batch;
		this.font  = font;
		this.x     = x;
		this.y     = y;
		
		// Add this Drawable
		DrawEngine.addDrawable(this);
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public void draw() {
		font.draw(batch, text, x, y);
	}
}
