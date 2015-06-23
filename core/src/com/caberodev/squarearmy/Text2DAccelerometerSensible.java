package com.caberodev.squarearmy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Text2DAccelerometerSensible extends Text2D implements Thinker {

	private float move_scale = 20;
	
	public Text2DAccelerometerSensible(SpriteBatch batch, BitmapFont font, float x, float y) {
		super(batch, font, x, y);
		LogicEngine.addThinker(this);
	}

	@Override
	public void think(float delta) {
		
		// Update based on movement direction
		this.x += InputEngine.dir.y * move_scale;
		this.y -= InputEngine.dir.x * move_scale;
		
		// Screen bounded
		if(this.x < 0) this.x = 0;
		if(this.y < 0) this.y = 0;
		if(Gdx.graphics.getWidth() < this.x) this.x = Gdx.graphics.getWidth();
		if(Gdx.graphics.getHeight() < this.y) this.y = Gdx.graphics.getHeight();
	}
}
