package com.caberodev.squarearmy.appearance;
import java.util.Random;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.caberodev.squarearmy.DrawEngine;
import com.caberodev.squarearmy.entity.Color;
import com.caberodev.squarearmy.main.Hero;


public class AppearanceHeroDamaged implements IRenderator {

	/* Constants */
	public static final int APPEARANCE_TIME = 15;
	private static final Color damagedColor = Color.WHITE;

	/* Variables */
	private ShapeRenderer shapeRenderer;
	private Hero hero;
	private IRenderator lastAppearance;
	private int remainingTime = APPEARANCE_TIME;
	private Random r = new Random();

	public AppearanceHeroDamaged(Hero hero, IRenderator lastAppearance) {
		this.shapeRenderer = DrawEngine.shapeRenderer;
		this.hero = hero;
		this.lastAppearance = lastAppearance;
	}

	public void render() {
		/* Get parameters */
		float x = hero.getX();
		float y = hero.getY();
		float size = hero.getSize();

		Color color;
		if (remainingTime % 2 == 0) {
			color = hero.getColor();
		} else {
			color = damagedColor;
		}

		int xOffset = r.nextInt(4) - 2;
		int yOffset = r.nextInt(4) - 2;
		
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(color.r, color.g, color.b, 1);
		shapeRenderer.rect(x + xOffset, y + yOffset, size, size);
		shapeRenderer.end();

		/* Logic */
		remainingTime--;
		/* If the time reaches 0 then we restore the last appearance */
		if (remainingTime <= 0) {
			hero.setAppearance(lastAppearance);
		}
	}

}
