package com.caberodev.squarearmy.appearance;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.caberodev.squarearmy.DrawEngine;
import com.caberodev.squarearmy.entity.EntityColor;

public class AppearanceSquare implements IRenderator {

	private IRenderable entity;
	private ShapeRenderer shapeRenderer;
	
	public AppearanceSquare(IRenderable entity) {
		this.entity = entity;
		this.shapeRenderer = DrawEngine.shapeRenderer;
	}

	public void render() {
		/* Get parameters */
		EntityColor color = entity.getColor();
		float x = entity.getX();
		float y = entity.getY();
		float size = entity.getSize();

		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(color.red, color.green, color.blue, 1);
		shapeRenderer.rect(x, y, size, size);	
		shapeRenderer.end();
	}

}
