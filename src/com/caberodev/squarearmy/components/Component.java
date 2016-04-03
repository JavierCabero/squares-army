package com.caberodev.squarearmy.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.caberodev.squarearmy.core.Hearer;
import com.caberodev.squarearmy.core.LogicEngine;
import com.caberodev.squarearmy.core.Thinker;
import com.caberodev.squarearmy.util.DataDictionary;
import com.caberodev.squarearmy.worldobjects.WorldObject;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 * A component can generate events and dynamically response to them sending messages to other components.
 */
public abstract class Component implements Thinker, Hearer {

	public final String name;
	protected WorldObject father;

	public DataDictionary data = new DataDictionary();
	protected HashMap<String, ArrayList<DataDictionary>> responses = new HashMap<String, ArrayList<DataDictionary>>(); // TODO: Loader;
	
	public Component(String name, WorldObject father) {
		this.name   = name;
		this.father = father;
	}
	
	public Component(String name) {
		this(name, null);
	}

	@Override
	public void hear(LinkedList<Hearer> sources, DataDictionary message) {
		// Nothing
	}

	@Override
	public void think(float delta) {
		// Nothing
	}

	protected void generateEvent(DataDictionary event) {
		switch(event._string("name")) {
		case "die":
			father.hear(null, event);
			break;
		}
	}

	public void close() {
		LogicEngine.delThinker(this);
	}
	
	public void bind(WorldObject father) {
		
		if (father == null)
			throw new ComponentException("Cannot set a null father to the component");
		
		this.father = father;
		LogicEngine.addThinker(this);
	}
	
	public WorldObject getFather() {
		return father;
	}

	// ToDo: implement responses
	public void addResponse(String key, ArrayList<DataDictionary> rs) {
		this.responses.put(key, rs);
	}
}
