package com.caberodev.squarearmy.world;

import com.caberodev.squarearmy.util.DataDictionary;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 * Copyright 2015 (c) All Rights Reserved. <br><br>
 *
 */
public class BehaviorLoader {

	@SuppressWarnings("rawtypes")
	public static Component loadBehavior(DataDictionary behaviorData) {
		Component behavior = null;
		switch (behaviorData._string("name")) {
		case "behavior":
			try {
				ClassLoader classLoader = BehaviorLoader.class.getClassLoader();
				Class aClass = classLoader.loadClass(behaviorData._string("behavior"));
				System.out.println("aClass.getName() = " + aClass.getName());
				try {
					// Try to create the new one
					behavior = (Component) aClass.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return behavior;
	}
}
