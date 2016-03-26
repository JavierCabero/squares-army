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

	// Global Data Pool
	public static DataDictionary global = new DataDictionary();
	
	private HashMap<String, Object> dictionary = new HashMap<String, Object>();
	
	public void set(String name, Object value) {
		dictionary.put(name, value);
	}
	
	public Object get(String name) {
		try { return dictionary.get(name); } catch(ClassCastException e) { return null; }
	}

	public Integer _int(String name) {
		try { if (dictionary.containsKey(name)) { return (int) dictionary.get(name); } else { return 0; } } catch(ClassCastException e) { return 0; }
	}

	public Boolean is(String name) {
		
		try { if (dictionary.containsKey(name)) { return (boolean) dictionary.get(name); } else { return false; } } catch(ClassCastException e) { return false; }
	}
	public Float _float(String name) {
		try { if (dictionary.containsKey(name)) { return (float) dictionary.get(name); } else { return 0f; } } catch(ClassCastException e) { if (dictionary.get(name) instanceof Integer) { return (float) (int) dictionary.get(name); } return 0f; }
	}

	public Double _double(String name) {
		try { if (dictionary.containsKey(name)) { return (double) dictionary.get(name); } else { return 0.0; } } catch(ClassCastException e) { if (dictionary.get(name) instanceof Integer) { return (double) (int) dictionary.get(name); } return 0.0; }
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