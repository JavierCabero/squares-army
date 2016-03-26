package com.caberodev.squarearmy.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.caberodev.squarearmy.Hearer;
import com.caberodev.squarearmy.LogicEngine;
import com.caberodev.squarearmy.Thinker;
import com.caberodev.squarearmy.util.DataDictionary;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 * Copyright 2015 (c) All Rights Reserved. <br><br>
 *        
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
		
		LogicEngine.addThinker(this);
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
		case "death":
			father.hear(null, event);
			break;
		}
	}

	public void close() {
		LogicEngine.delThinker(this);
	}
}
