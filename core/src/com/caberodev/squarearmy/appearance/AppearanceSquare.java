package com.caberodev.squarearmy.appearance;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.caberodev.squarearmy.entity.Color;

public class AppearanceSquare extends Shape implements IRenderator {

	private IRenderable entity;
	
	private float x, y;
	private float size;
	
	public AppearanceSquare(IRenderable entity) {
		this.entity = entity;
	}

	public void render() {
		Color color = entity.getColor();
		x = entity.getX();
		y = entity.getY();
		size = entity.getSize();

		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(color.r, color.g, color.b, 1);
		shapeRenderer.rect(x, y, size, size);	
		shapeRenderer.end();
	}
}
