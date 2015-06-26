package com.caberodev.squarearmy.world;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 * Copyright 2015 (c) All Rights Reserved. <br><br>
 *       
 */
public class ListLinker {

	private static HashMap<String, ArrayList<WorldObject>> linker;
	
	public static void add(WorldObject wo) {
		ArrayList<WorldObject> list = linker.get(wo.getClass().getName());
		if(list == null) {
			list = new ArrayList<WorldObject>();
			linker.put(wo.getClass().getName(), list);
		}
		list.add(wo);
	}
	
	public static void del(WorldObject wo) {
		ArrayList<WorldObject> list = linker.get(wo.getClass().getName());
		if(list != null) 
			list.remove(wo);
	}
}
