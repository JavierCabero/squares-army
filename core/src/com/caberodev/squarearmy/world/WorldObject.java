package com.caberodev.squarearmy.world;

import com.caberodev.squarearmy.Drawer;
import com.caberodev.squarearmy.Hearer;
import com.caberodev.squarearmy.Thinker;
import com.caberodev.squarearmy.util.DataDictionary;
import com.caberodev.squarearmy.util.ListLinker;

/**
 * 
 * Author: Javier Cabero Guerra <br>
 * 
 * Copyright 2015 (c) All Rights Reserved. <br><br>
 *        
 * A WorldObject is the class that every object in the world extends. 
 * <br>It provides:<br> 
 * <ul>
 *   <li>Location (by its connection with the Octree)</li>
 *   <li>List linkage</li>
 *   <li> TODO: Oriented Bounding Box</li>
 * </ul>
 */
public abstract class WorldObject implements Thinker, Drawer, Hearer {

	// Position in world
	public float x, y;
	public float dx, dy;
	
	public DataDictionary dataDictionary;
	
	public WorldObject() {
		
		// Initialize DataDictionary
		dataDictionary = new DataDictionary();
		
		// Create a list with all instances of the child class
		ListLinker.add(this);
	}
	
	public WorldObject(float x, float y) {
		this();
		this.x = x;
		this.y = y;
	}
	
	/**
	 * This method NEEDS to be called. It removes the instance from the list of its class. 
	 */
	public void close() {
		
		// Delete the method
		ListLinker.del(this);
	}
	
	@Override
	public void think(float delta){
		x += dx;
		y += dy;
		
		movementReduction();
	}
	
	private void movementReduction() {
		/* Movement reduction */
		if (dx > 0) {
			dx--;
			if (dx < 1) {
				dx = 0;
			}
		} else if (dx < 0) {
			dx++;
			if (dx > -1) {
				dx = 0;
			}
		}
		if (dy > 0) {
			dy--;
			if (dy < 1) {
				dy = 0;
			}
		} else if (dy < 0) {
			dy++;
			if (dy > -1) {
				dy = 0;
			}
		}
	}
	
	@Override
	public void draw() {
		// Nothing
	}

	
	@Override
	public void hear() {
		// Nothing
	}
}
