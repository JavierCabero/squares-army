package com.caberodev.squarearmy.appearance;
import java.util.Random;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.caberodev.squarearmy.util.Color;
import com.caberodev.squarearmy.world.Minion;

public class AppearanceMinionDamaged extends ShapeDrawer implements IRenderator {

	/* Constants */
	private static final int APPEARANCE_TIME = 15;
	private static final Color damagedColor = Color.WHITE;

	
	/* Variables */
	private Minion minion;
//	private IRenderator lastAppearance;
	private int remainingTime = APPEARANCE_TIME;
	private Random r = new Random();

	public AppearanceMinionDamaged(Minion minion, IRenderator lastAppearance) {
		this.minion = minion;
//		this.lastAppearance = lastAppearance;
	}

	public void render() {
		/* Get parameters */
		float x = minion.x;
		float y = minion.y;
		float size = minion.size;

		Color color;
		if (remainingTime % 2 == 0) {
			color = minion.getColor();
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
			// TODO: Refactor this class as drawer effect
//			minion.setAppearance(lastAppearance);
		}
	}

}
