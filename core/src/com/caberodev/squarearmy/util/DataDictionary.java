package com.caberodev.squarearmy.util;

import java.util.HashMap;

/**
 * 
 * Author: Javier Cabero Guerra <br>
 * Copyright 2015 (c) All Rights Reserved. <br><br>
 * 
 *  Map wrapper with casting methods for convenience.
 *  
 */
public class DataDictionary {

	private HashMap<String, Object> dictionary = new HashMap<String, Object>();
	
	public void set(String name, Object value) {
		dictionary.put(name, value);
	}
	
	public Object get(String name) {
		try { return dictionary.get(name); } catch(ClassCastException e) { return null; }
	}

	public Integer getInt(String name) {
		try { return (Integer) dictionary.get(name); } catch(ClassCastException e) { return 0; }
	}

	public Boolean is(String name) {
		try { return (Boolean) dictionary.get(name); } catch(ClassCastException e) { return false; }
	}
	public Float getFloat(String name) {
		try { return (Float)   dictionary.get(name); } catch(ClassCastException e) { return 0f; }
	}

	public Double getDouble(String name) {
		try { return (Double)  dictionary.get(name); } catch(ClassCastException e) { return 0.0; }
	}

	public String getString(String name) {
		try { return String.valueOf(dictionary.get(name)); } catch(ClassCastException e) { return ""; }
	}	
}