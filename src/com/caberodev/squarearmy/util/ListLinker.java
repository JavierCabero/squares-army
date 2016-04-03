package com.caberodev.squarearmy.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.caberodev.squarearmy.worldobjects.WorldObject;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 * Allows associations of lists under key names.
 *       
 */
public class ListLinker {

	private static HashMap<String, ArrayList<WorldObject>> linker = new HashMap<String, ArrayList<WorldObject>>();
	private static HashMap<WorldObject, ArrayList<String>> links  = new HashMap<WorldObject, ArrayList<String>>(); 
	
	/**
	 * Adds the given WorldObject to its list.
	 */
	public static void add(WorldObject o) {
		Class<?> c = o.getClass();
		while (!c.equals(Object.class)) {
			String key = c.getSimpleName();
			ArrayList<WorldObject> l1 = linker.get(key);
			ArrayList<String>      l2 = links.get(o);
			if (l1 == null) {
				l1 = new ArrayList<WorldObject>();
				linker.put(key, l1);
			}
			if (l2 == null) {
				l2 = new ArrayList<String>();
				links.put(o, l2);
			}
			l1.add(o);
			l2.add(key);
			c = c.getSuperclass();
			o.data.setColor("color", Color.CYAN);
		}
	}
	
	/**
	 * Adds the Objects in the collection to their list.
	 */
	public static void addAll(Collection<? extends WorldObject> collection) {
		for(WorldObject o : collection) {
			Class<?> c = o.getClass();
			while (!c.equals(Object.class)) {
				String key = c.getSimpleName();
				ArrayList<WorldObject> l1 = linker.get(key);
				ArrayList<String>      l2 = links.get(o);
				if(l1 == null) {
					l1 = new ArrayList<WorldObject>();
					linker.put(key, l1);
					l2 = new ArrayList<String>();
					links.put(o, l2);
				}
				l1.add(o);
				l2.add(key);
				c = c.getSuperclass();
			}
		}
	}
	
//	/**
//	 * Adds the given WorldObject to key list.
//	 */
//	public static void add(String key, WorldObject o) {
//		ArrayList<WorldObject> list = linker.get(key);
//		if(list == null) {
//			list = new ArrayList<WorldObject>();
//			linker.put(key, list);
//		}
//		list.add(o);
//	}
//	
//	/**
//	 * Adds the Objects in the collection to key list.
//	 */
//	public static void addAll(String key, Collection<? extends WorldObject> c) {
//		for(WorldObject o : c) {
//			ArrayList<WorldObject> list = linker.get(key);
//			if(list == null) {
//				list = new ArrayList<WorldObject>();
//				linker.put(key, list);
//			}
//			list.add(o);
//		}
//	}
	
	/**
	 * Deletes the given WorldObject from its list.
	 */
	public static void del(WorldObject o) {
		for (String key : links.get(o)) {
			ArrayList<WorldObject> list = linker.get(key);
			list.remove(o);
			o.data.setColor("color", Color.RED);
		}
	}
	
	/**
	 * Deletes the Objects in the collection from their list.
	 */
	public static void delAll(Collection<? extends WorldObject> c) {
		for(WorldObject o : c) {
			for (String key : links.get(o)) {
				ArrayList<WorldObject> list = linker.get(key);
				list.remove(o);
				o.data.setColor("color", Color.RED);
			}
		}
	}
//	
//	/**
//	 * Deletes the given WorldObject from key list.
//	 */
//	public static void del(String key, WorldObject o) {
//		ArrayList<WorldObject> list = linker.get(key);
//		if(list != null) 
//			list.remove(o);
//	}
//	
//	/**
//	 * Deletes the Objects in the collection from key list.
//	 */
//	public static void delAll(String key, Collection<? extends WorldObject> c) {
//		for(WorldObject o : c) {
//			ArrayList<WorldObject> list = linker.get(key);
//			if(list != null) 
//				list.remove(o);
//		}
//	}
	
	/**
	 * Returns the Objects list associated with the given key. If such association doesn't exists returns null. 
	 */
	public static ArrayList<WorldObject> get(String key) {
		ArrayList<WorldObject> list = linker.get(key);
		if (list == null)
			list = new ArrayList<WorldObject>();
		linker.put(key, list);
		return list;
	}
	
	/**
	 * Returns the number of objects in the given list or throws an NullPointerException if the list doesn't exist 
	 */
	public static int count(String key) {
		return linker.get(key) != null ? linker.get(key).size() : 0;
	}
	
}
