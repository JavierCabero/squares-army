package com.caberodev.squarearmy.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 * Copyright 2015 (c) All Rights Reserved. <br><br>
 * 
 * Allows associations of lists under key names.
 *       
 */
public class ListLinker {

	private static HashMap<String, ArrayList<Object>> linker = new HashMap<String, ArrayList<Object>>();
	
	/**
	 * Adds the given Object to its list.
	 */
	public static void add(Object o) {
		String			   key = o.getClass().getName();
		ArrayList<Object> list = linker.get(key);
		if(list == null) {
			list = new ArrayList<Object>();
			linker.put(key, list);
		}
		list.add(o);
	}
	
	/**
	 * Adds the Objects in the collection to their list.
	 */
	public static void addAll(Collection<? extends Object> c) {
		for(Object o : c) {
			String			   key = o.getClass().getName();
			ArrayList<Object> list = linker.get(key);
			if(list == null) {
				list = new ArrayList<Object>();
				linker.put(key, list);
			}
			list.add(o);
		}
	}
	
	/**
	 * Adds the given Object to key list.
	 */
	public static void add(String key, Object o) {
		ArrayList<Object> list = linker.get(key);
		if(list == null) {
			list = new ArrayList<Object>();
			linker.put(key, list);
		}
		list.add(o);
	}
	
	/**
	 * Adds the Objects in the collection to key list.
	 */
	public static void addAll(String key, Collection<? extends Object> c) {
		for(Object o : c) {
			ArrayList<Object> list = linker.get(key);
			if(list == null) {
				list = new ArrayList<Object>();
				linker.put(key, list);
			}
			list.add(o);
		}
	}
	
	/**
	 * Deletes the given Object from its list.
	 */
	public static void del(Object o) {
		String			   key = o.getClass().getName();
		ArrayList<Object> list = linker.get(key);
		if(list != null) 
			list.remove(o);
	}
	
	/**
	 * Deletes the Objects in the collection from their list.
	 */
	public static void delAll(Collection<? extends Object> c) {
		for(Object o : c) {
			String			   key = o.getClass().getName();
			ArrayList<Object> list = linker.get(key);
			if(list != null) 
				list.remove(o);
		}
	}
	
	/**
	 * Deletes the given Object from key list.
	 */
	public static void del(String key, Object o) {
		ArrayList<Object> list = linker.get(key);
		if(list != null) 
			list.remove(o);
	}
	
	/**
	 * Deletes the Objects in the collection from key list.
	 */
	public static void delAll(String key, Collection<? extends Object> c) {
		for(Object o : c) {
			ArrayList<Object> list = linker.get(key);
			if(list != null) 
				list.remove(o);
		}
	}
	
	/**
	 * Returns the Objects list associated with the given key. If such association doesn't exists returns null. 
	 */
	public static ArrayList<Object> get(String key) {
		return linker.get(key);
	}
}
