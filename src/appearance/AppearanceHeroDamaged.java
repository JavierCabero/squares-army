package appearance;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.util.Random;

import main.Hero;


import entity.EntityColor;


public class AppearanceHeroDamaged implements IRenderator{

	/* Constants */
	public static final int APPEARANCE_TIME = 15;
	private static final EntityColor damagedColor = EntityColor.WHITE;

	/* Variables */
	private Hero hero;
	private IRenderator lastAppearance;
	private int remainingTime = APPEARANCE_TIME;
	private Random r = new Random();

	public AppearanceHeroDamaged(Hero hero, IRenderator lastAppearance) {
		this.hero = hero;
		this.lastAppearance = lastAppearance;
	}

	public void render() {
		/* Get parameters */
		float x = hero.getX();
		float y = hero.getY();
		float size = hero.getSize();

		EntityColor color;
		if (remainingTime % 2 == 0) {
			color = hero.getColor();
		} else {
			color = damagedColor;
		}

		/* Render */
		/* Color */
		glColor3f(color.red, color.green, color.blue);

		int xOffset = r.nextInt(4) - 2;
		int yOffset = r.nextInt(4) - 2;

		glBegin(GL_QUADS);
		glVertex2f(x + xOffset, y + yOffset); // Upper-left
		glVertex2f(x + xOffset + size, y + yOffset); // Upper-right
		glVertex2f(x + xOffset + size, y + yOffset + size); // Bottom-right
		glVertex2f(x + xOffset, y + yOffset + size); // Botton-left
		glEnd();

		/* Logic */
		remainingTime--;
		/* If the time reaches 0 then we restore the last appearance */
		if (remainingTime <= 0) {
			hero.setAppearance(lastAppearance);
		}
	}

}
