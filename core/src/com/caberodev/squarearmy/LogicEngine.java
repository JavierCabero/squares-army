package com.caberodev.squarearmy;

import java.util.ArrayList;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 * Copyright 2015 (c) All Rights Reserved.
 *                                          
 */
public class LogicEngine {

	private static long previous = System.currentTimeMillis();
	
	private static ArrayList<Thinker> thinkList = new ArrayList<Thinker>();

	public static void start() {
		// Nothing
	}
	
	public static void addThinker(Thinker item) {
		thinkList.add(item);
	}
	
	public static void delThinker(Thinker item) {
		thinkList.remove(item);
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
	}
}
