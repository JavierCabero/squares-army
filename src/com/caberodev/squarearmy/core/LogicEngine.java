package com.caberodev.squarearmy.core;

import java.util.ArrayList;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 */
public class LogicEngine {

	private static long previous = System.currentTimeMillis();
	
	private static ArrayList<Thinker> thinkList  = new ArrayList<Thinker>();
	private static ArrayList<Thinker> removeList = new ArrayList<Thinker>();
	private static ArrayList<Thinker> addList    = new ArrayList<Thinker>();
	
	public static void start() {
		// Nothing
	}
	
	public static void addThinker(Thinker item) {
		addList.add(item);
	}
	
	public static void delThinker(Thinker item) {
		removeList.add(item);
	}
	
	private static float getDelta() {
		long current = System.currentTimeMillis();
		float delta = (current - previous) / 1000f;
		previous = current;
		return delta;
	}
	
	public static void update() {
		
		float delta = getDelta();
		
		for(Thinker item : thinkList)
			item.think(delta);
		
		thinkList.addAll(addList);
		addList.clear();
		thinkList.removeAll(removeList);
		removeList.clear();
	}
}
