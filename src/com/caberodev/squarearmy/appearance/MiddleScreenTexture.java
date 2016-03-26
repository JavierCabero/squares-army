package com.caberodev.squarearmy.appearance;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import com.caberodev.squarearmy.util.Color;

public class MiddleScreenTexture {

	private Texture texture;
	private float x, y;

	public MiddleScreenTexture(Texture texture) {
		this.texture = texture;

		x = Display.getWidth() / 2 - texture.getImageWidth() / 2;
		y = Display.getHeight() / 2 - texture.getImageHeight() / 2;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void render() {

		texture.bind();

		/*
		glLoadIdentity();

		glColor3f(1f, 1f, 1f);

		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(x, y + texture.getImageHeight()); // Upper-left
		glTexCoord2f(texture.getWidth(), 0);
		glVertex2f(x + texture.getImageWidth(), y + texture.getImageHeight()); // Upper-right
		glTexCoord2f(texture.getWidth(), texture.getHeight());
		glVertex2f(x + texture.getImageWidth(), y); // Bottom-right
		glTexCoord2f(0, texture.getHeight());
		glVertex2f(x, y); // Botton-left
		glEnd();
		*/
	}

	public void update() {
	}

	public Color getColor() {
		return Color.WHITE;
	}

	public float getSize() {
		return -1;
	}
}
