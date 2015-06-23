package com.caberodev.squarearmy.appearance;
import com.caberodev.squarearmy.entity.Entity;
import com.caberodev.squarearmy.entity.Color;


public interface IRenderable extends Entity {

	public void update();
	public Color getColor();
	public float getSize();
	
}
