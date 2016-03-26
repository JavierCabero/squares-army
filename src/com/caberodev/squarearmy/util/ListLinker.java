package com.caberodev.squarearmy.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.caberodev.squarearmy.world.WorldObject;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 * Allows associations of lists under key names.
 *       
 */
public class ListLinker {

	private static HashMap<String, ArrayList<WorldObject>> linker = new HashMap<String, ArrayList<WorldObject>>();
	
	/**
	 * Adds the given WorldObject to its list.
	 */
	public static void add(WorldObject o) {
		String key = o.getClass().getName().toLowerCase();
		ArrayList<WorldObject> list = linker.get(key);
		if(list == null) {
			list = new ArrayList<WorldObject>();
			linker.put(key, list);
		}
		list.add(o);
	}
	
	/**
	 * Adds the Objects in the collection to their list.
	 */
	public static void addAll(Collection<? extends WorldObject> c) {
		for(WorldObject o : c) {
			String key = o.getClass().getName();
			ArrayList<WorldObject> list = linker.get(key);
			if(list == null) {
				list = new ArrayList<WorldObject>();
				linker.put(key, list);
			}
			list.add(o);
		}
	}
	
	/**
	 * Adds the given WorldObject to key list.
	 */
	public static void add(String key, WorldObject o) {
		ArrayList<WorldObject> list = linker.get(key);
		if(list == null) {
			list = new ArrayList<WorldObject>();
			linker.put(key, list);
		}
		list.add(o);
	}
	
	/**
	 * Adds the Objects in the collection to key list.
	 */
	public static void addAll(String key, Collection<? extends WorldObject> c) {
		for(WorldObject o : c) {
			ArrayList<WorldObject> list = linker.get(key);
			if(list == null) {
				list = new ArrayList<WorldObject>();
				linker.put(key, list);
			}
			list.add(o);
		}
	}
	
	/**
	 * Deletes the given WorldObject from its list.
	 */
	public static void del(WorldObject o) {
		String key = o.getClass().getName();
		ArrayList<WorldObject> list = linker.get(key);
		if(list != null) 
			list.remove(o);
	}
	
	/**
	 * Deletes the Objects in the collection from their list.
	 */
	public static void delAll(Collection<? extends WorldObject> c) {
		for(WorldObject o : c) {
			String key = o.getClass().getName();
			ArrayList<WorldObject> list = linker.get(key);
			if(list != null) 
				list.remove(o);
		}
	}
	
	/**
	 * Deletes the given WorldObject from key list.
	 */
	public static void del(String key, WorldObject o) {
		ArrayList<WorldObject> list = linker.get(key);
		if(list != null) 
			list.remove(o);
	}
	
	/**
	 * Deletes the Objects in the collection from key list.
	 */
	public static void delAll(String key, Collection<? extends WorldObject> c) {
		for(WorldObject o : c) {
			ArrayList<WorldObject> list = linker.get(key);
			if(list != null) 
				list.remove(o);
		}
	}
	
	/**
	 * Returns the Objects list associated with the given key. If such association doesn't exists returns null. 
	 */
	public static ArrayList<WorldObject> get(String key) {
		return linker.get(key);
	}
	
	/**
	 * Returns the number of objects in the given list or throws an NullPointerException if the list doesn't exist 
	 */
	public static int count(String key) {
		return linker.get(key).size();
	}
}
