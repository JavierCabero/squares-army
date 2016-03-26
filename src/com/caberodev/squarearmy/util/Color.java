package com.caberodev.squarearmy.util;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 * Copyright 2015 (c) All Rights Reserved.
 *                                          
 */
public class Color {
	
	public static final Color    RED = new Color(1f,0f,0f);
	public static final Color  GREEN = new Color(0f,1f,0f);
	public static final Color   BLUE = new Color(0f,0f,1f); 
	public static final Color   CYAN = new Color(0f, 0.9f, 0.9f);
	public static final Color   GRAY = new Color(0.8f, 0.8f, 0.8f);
	public static final Color YELLOW = new Color(1f, 1f, 0f);
	public static final Color  WHITE = new Color(1f, 1f, 1f);
	
	public float r, g, b, a;

	public Color(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = 1f;
	}

	public Color(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public float[] asArray() {
		return new float[]{r, g, b, a};
	}

	public static Color random() {
		switch(RandomData.nextInt(7)) {
		case 0:
			return RED;
		case 1:
			return GREEN;
		case 2:
			return BLUE;
		case 3:
			return CYAN;
		case 4:
			return GRAY;
		case 5:
			return YELLOW;
		default:
			return WHITE;
		}
	}
	
	@Override 
	public String toString() {
		return "(" + r + ", " + g + ", " + b + ", " + a + ")";
	}
}
