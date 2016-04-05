package com.caberodev.squarearmy.worldobjects;

import java.util.HashMap;
import java.util.LinkedList;

import com.caberodev.squarearmy.components.Component;
import com.caberodev.squarearmy.core.Hearer;
import com.caberodev.squarearmy.core.LogicEngine;
import com.caberodev.squarearmy.core.Thinker;
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

	public DataDictionary data = new DataDictionary();
	
	public HashMap<String, Component> components = new HashMap<String, Component>();
	
//	private int id_seq = 0;
//	private int id     = id_seq++;
	
	public WorldObject() {
		this(null);
	}
	
	public WorldObject(DataDictionary data) {
		
		if(data != null)		
			this.data = data;
		
		// Class is set in the DataDictionary
		this.data.setString("class", getClass().getSimpleName());
		
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
		data.setFloat("x", data._float("x") + data._float("dx"));
		data.setFloat("y", data._float("y") + data._float("dy"));
		
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
		
		data.setFloat("dx", dx);
		data.setFloat("dy", dy);
	}

	@Override
	public void hear(LinkedList<Hearer> sources, DataDictionary message) {
		
		String name = message._string("name");
		
		if (name.equals("die")) {
			close();
			for(Component cmp : components.values()) 
				cmp.close();
			components = null;
		}
		

		// distribute to all components
		for (Component cmp : components.values())
			cmp.hear(sources, message);
	}


	/**
	 * Adds the given component to the WorldObject
	 */
	public void addComponent(Component component) {
		components.put(component.name, component);
		component.bind(this);
	}
}
