package com.caberodev.squarearmy.world;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;

import com.caberodev.squarearmy.core.LivingFactory;
import com.caberodev.squarearmy.core.WorldObject;
import com.caberodev.squarearmy.util.DataDictionary;
import com.caberodev.squarearmy.util.ListLinker;
import com.caberodev.squarearmy.util.RandomData;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 * Copyright 2015 (c) All Rights Reserved. <br><br>
 *
 */
public class MinionGenerator extends WorldObject {

	private ArrayList<WorldObject> minions = (ArrayList<WorldObject>) ListLinker.get("minions");
	
	public MinionGenerator() {
		data.set("MINIONS_MAX_COUNT", 350);
		data.set("MINIONS_START_COUNT", 50);
		data.set("MINIONS_MAX_X_DISTANCE", Display.getWidth());
		data.set("MINIONS_MAX_Y_DISTANCE", Display.getHeight());
		
		// Create Minions 
		for (int i = 1; i < data._int("MINIONS_START_COUNT"); i++) {
			
			DataDictionary minion_data = new DataDictionary();

			minion_data.set("x", RandomData.nextInt(data._int("MINIONS_MAX_X_DISTANCE")));
			minion_data.set("y", RandomData.nextInt(data._int("MINIONS_MAX_Y_DISTANCE")));
			minion_data.set("behavior", "idle");
			
			LivingFactory.create("minion", minion_data);
		}
	}
	
	@Override
	public void think(float delta) {

		// Get the player data
		float playerX = DataDictionary.global._float("playerX"); 
		float playerY = DataDictionary.global._float("playerY");
		
		// Add minions around player
		if (ListLinker.count("minions") < data._int("MINIONS_MAX_COUNT")) {
			int x, y;
			int i = RandomData.nextInt(data._int("MINIONS_MAX_X_DISTANCE"));
			int j = RandomData.nextInt(data._int("MINIONS_MAX_Y_DISTANCE"));
			if (i % 2 == 0) {
				x = (int) playerX + Display.getWidth() / 2 + i;
				if (j % 2 == 0) {
					y = (int) playerY + Display.getHeight() / 2 + j;
				} else {
					y = (int) playerY - Display.getHeight() / 2 - j;
				}
			} else {
				x = (int) playerX - Display.getWidth() / 2 - i;
				if (j % 2 == 0) {
					y = (int) playerY + Display.getHeight() / 2 + j;
				} else {
					y = (int) playerY - Display.getHeight() / 2 - j;
				}
			}
			
			DataDictionary data = new DataDictionary();
			data.set("x", x);
			data.set("y", y);
			
			LivingFactory.create("minion", data);
		}
	}
}
