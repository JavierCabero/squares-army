package com.caberodev.squarearmy.appearance;
import com.caberodev.squarearmy.entity.Entity;
import com.caberodev.squarearmy.entity.EntityColor;


public interface IRenderable extends Entity {

	public void update();
	public EntityColor getColor();
	public float getSize();
	
}
