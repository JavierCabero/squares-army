package com.caberodev.squarearmy.components.graphics;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import com.caberodev.squarearmy.util.Color;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 * Holds square geometry data and draws it.
 */
public class SquareDrawer extends Drawer {

	public float x, y;
	public float size  = 0f;
	public Color color = Color.WHITE;

	@Override
	public void think(float delta) {
		
		x     = father.data._float("x");
		y 	  = father.data._float("y");
		size  = father.data._float("size");
		color = father.data._color("color");
	}
	
	@Override
	public void draw() {

		glColor4f(color.r, color.g, color.b, 1);
		glBegin(GL_QUADS);
			glVertex2f(x, y); 
			glVertex2f(x, y + size); 
			glVertex2f(x + size, y + size); 
			glVertex2f(x + size, y); 
		glEnd();
		
		//System.out.println("Drawing square to: (" + x + ", " + y + ") Size: " + size + " Color: " + color);
		
	}
}
