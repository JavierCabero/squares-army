package com.caberodev.squarearmy.appearance;
import java.util.Random;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.caberodev.squarearmy.entity.EntityColor;
import com.caberodev.squarearmy.main.Minion;

public class AppearanceMinionDamaged implements IRenderator {

	/* Constants */
	private static final int APPEARANCE_TIME = 15;
	private static final EntityColor damagedColor = EntityColor.WHITE;

	
	/* Variables */
	private ShapeRenderer shapeRenderer;
	private Minion minion;
	private IRenderator lastAppearance;
	private int remainingTime = APPEARANCE_TIME;
	private Random r = new Random();

	public AppearanceMinionDamaged(Minion minion, IRenderator lastAppearance) {
		this.shapeRenderer = new ShapeRenderer();
		this.minion = minion;
		this.lastAppearance = lastAppearance;
	}

	public void render() {
		/* Get parameters */
		float x = minion.getX();
		float y = minion.getY();
		float size = minion.getSize();

		EntityColor color;
		if (remainingTime % 2 == 0) {
			color = minion.getColor();
		} else {
			color = damagedColor;
		}

		int xOffset = r.nextInt(4) - 2;
		int yOffset = r.nextInt(4) - 2;
		
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(color.red, color.green, color.blue, 1);
		shapeRenderer.rect(x + xOffset, y + yOffset, size, size);
		shapeRenderer.end();
		
		/* Logic */
		remainingTime--;
		/* If the time reaches 0 then we restore the last appearance */
		if (remainingTime <= 0) {
			minion.setAppearance(lastAppearance);
		}
	}

}
