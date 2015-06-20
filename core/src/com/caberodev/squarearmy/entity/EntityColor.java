package com.caberodev.squarearmy.entity;

public enum EntityColor {
	
	RED(1f,0f,0f),
	GREEN(0f,1f,0f),
	BLUE(0f,0f,1f), 
	CIAN(0f, 0.9f, 0.9f),
	GRAY(0.8f, 0.8f, 0.8f),
	YELLOW(1f, 1f, 0f),
	WHITE(1f, 1f, 1f);
	
	public final float red, green, blue;

	EntityColor(float red, float green, float blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
}
