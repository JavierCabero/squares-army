package com.caberodev.squarearmy.util;

import java.util.HashMap;

import com.caberodev.squarearmy.components.Component;
import com.caberodev.squarearmy.worldobjects.WorldObject;

/**
 * 
 * Author: Javier Cabero Guerra <br>
 * Copyright 2015 (c) All Rights Reserved. <br><br>
 * 
 *  Map wrapper with casting methods for convenience.
 *  
 */
public class DataDictionary {

	// Global Data Pool
	public static DataDictionary global = new DataDictionary();
	
	private HashMap<String, Object> dictionary = new HashMap<String, Object>();
	
	public void setString(String name, String value) {
		dictionary.put(name, value);
	}
	
	public void setFloat(String name, Float value) {
		dictionary.put(name, value);
	}

	public void setDataDictionary(String name, DataDictionary data) {
		dictionary.put(name, data);
	}
	
	public void setComponent(String name, Component cmp) {
		dictionary.put(name, cmp);
	}
	
	public void setWorldObject(String name, WorldObject wo) {
		dictionary.put(name, wo);
	}
	
	public void setColor(String name, Color color) {
		dictionary.put(name, color);
	}
	
	public Boolean is(String name) {
		try { if (dictionary.containsKey(name)) { return (boolean) dictionary.get(name); } else { return false; } } catch(ClassCastException e) { return false; }
	}
	public Float _float(String name) {
		try { if (dictionary.containsKey(name)) { return (float) dictionary.get(name); } else { return 0f; } } catch(ClassCastException e) { if (dictionary.get(name) instanceof Integer) { return (float) (int) dictionary.get(name); } return 0f; }
	}

	public String _string(String name) {
		try { if (dictionary.containsKey(name)) { return (String) dictionary.get(name); } else { return ""; } } catch(ClassCastException e) { return ""; }
	}	
	
	public DataDictionary _data(String name) {
		try { if (dictionary.containsKey(name)) { return (DataDictionary) dictionary.get(name); } else { return null; } } catch(ClassCastException e) { return null; }
	}

	public Color _color(String name) {
		try { if (dictionary.containsKey(name)) { return (Color) dictionary.get(name); } else { return Color.WHITE; } } catch(ClassCastException e) { return Color.WHITE; }
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{\n");
		for(String key : dictionary.keySet()) 
			sb.append("  " + key + ": " + dictionary.get(key) + ",\n");
		sb.deleteCharAt(sb.length()-2); // remove last comma
		sb.append("}");
		return sb.toString();
	}
}