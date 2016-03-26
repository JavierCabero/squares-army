package com.caberodev.squarearmy.world;

import java.util.HashMap;
import java.util.LinkedList;

import com.caberodev.squarearmy.Drawer;
import com.caberodev.squarearmy.Hearer;
import com.caberodev.squarearmy.LogicEngine;
import com.caberodev.squarearmy.Thinker;
import com.caberodev.squarearmy.util.DataDictionary;
import com.caberodev.squarearmy.util.ListLinker;

/**
 * 
 * Author: Javier Cabero Guerra <br>
 * 
 * A WorldObject is the class that every object in the world extends. 
 * <br>It provides:<br> 
 * <ul>
 *   <li>Location (by its connection with the Octree)</li>
 *   <li>List linkage</li>
 *   <li> TODO: Oriented Bounding Box</li>
 * </ul>
 */
public class WorldObject implements Thinker, Hearer {

	public DataDictionary data;
	
	public HashMap<String, Component> components;
	
	private int id_seq = 0;
	private int id = id_seq++;
	
	public WorldObject() {
		
		// Initialize DataDictionary
		data = new DataDictionary();
		
		// Initialize component structure
		components = new HashMap<String, Component>();
		
		// Create a list with all instances of the child class
		ListLinker.add(this);
		LogicEngine.addThinker(this);
	}
	
	public WorldObject(DataDictionary data) {
		
		// Initialize DataDictionary
		this.data = data;
		
		// Initialize component structure
		components = new HashMap<String, Component>();
		
		// Create a list with all instances of the child class
		ListLinker.add(this);
		LogicEngine.addThinker(this);
	}
	
	/**
	 * This method NEEDS to be called. It removes the instance from the list of its class. 
	 */
	public void close() {
		
		// Delete the method
		ListLinker.del(this);
		LogicEngine.delThinker(this);
	}
	
	@Override
	public void think(float delta) {
		data.set("x", data._float("x") + data._float("dx"));
		data.set("y", data._float("y") + data._float("dy"));
		
		movementReduction();
	}
	
	private void movementReduction() {
		float dx = data._float("dx");
		float dy = data._float("dy");
		
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
		
		data.set("dx", dx);
		data.set("dy", dy);
	}

	@Override
	public void hear(LinkedList<Hearer> sources, DataDictionary message) {
		switch(message._string("name")) {
		case "death":
			close();
			for(Component cmp : components.values()) 
				cmp.close();
			components = null;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorldObject other = (WorldObject) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
