package com.caberodev.squarearmy.world;

import java.util.ArrayList;
import java.util.LinkedList;

import com.caberodev.squarearmy.Hearer;
import com.caberodev.squarearmy.util.DataDictionary;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 * Copyright 2015 (c) All Rights Reserved. <br><br>
 *
 */
public class MinionCollector extends Component {

	private ArrayList<WorldObject> minions;
	
	public MinionCollector(WorldObject father) {
		super("minionCollector", father);
		minions = new ArrayList<WorldObject>();
	}
	
	@Override
	public void think(float delta) {
		// TODO: Search for minions
	}
	
	@Override
	public void hear(LinkedList<Hearer> sources, DataDictionary message) {
		switch(message._string("name")) {
		
		}
	}
}
