package com.caberodev.squarearmy.appearance;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.caberodev.squarearmy.Drawer;
import com.caberodev.squarearmy.util.Color;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 * Copyright 2015 (c) All Rights Reserved. <br><br>
 * 
 * Holds rectangle data and draws it.
 */
public class SquareDrawer extends ShapeDrawer implements Drawer {

	public float x, y;
	public float size  = 1f;
	public Color color = Color.WHITE;
	
	public void draw() {
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(color.r, color.g, color.b, 1);
		shapeRenderer.rect(x, y, size, size);	
		shapeRenderer.end();
	}
}
