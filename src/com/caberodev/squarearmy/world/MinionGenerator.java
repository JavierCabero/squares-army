package com.caberodev.squarearmy.world;

import org.lwjgl.opengl.Display;

import com.caberodev.squarearmy.util.DataDictionary;
import com.caberodev.squarearmy.util.ListLinker;
import com.caberodev.squarearmy.util.RandomData;
import com.caberodev.squarearmy.worldobjects.Minion;
import com.caberodev.squarearmy.worldobjects.WorldObject;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 * Copyright 2015 (c) All Rights Reserved. <br><br>
 *
 */
public class MinionGenerator extends WorldObject {

//	private ArrayList<WorldObject> minions = (ArrayList<WorldObject>) ListLinker.get("Minion");
	
	public MinionGenerator() {
		data.setFloat("MINIONS_MAX_COUNT",  350f);
		data.setFloat("MINIONS_START_COUNT", 50f);
		data.setFloat("MINIONS_MAX_X_DISTANCE", (float) Display.getWidth());
		data.setFloat("MINIONS_MAX_Y_DISTANCE", (float) Display.getHeight());
		
		// Create Minions 
		for (int i = 1; i < data._float("MINIONS_START_COUNT"); i++) {
			
			DataDictionary minion_data = new DataDictionary();

			minion_data.setFloat("x", (float) RandomData.nextInt(data._float("MINIONS_MAX_X_DISTANCE").intValue())-Display.getWidth()/2);
			minion_data.setFloat("y", (float) RandomData.nextInt(data._float("MINIONS_MAX_Y_DISTANCE").intValue())-Display.getHeight()/2);
			minion_data.setString("behavior", "idle");
			
			new Minion(minion_data);
		}
	}
	
	@Override
	public void think(float delta) {

		// Get the player data
		float playerX = DataDictionary.global._float("playerX"); 
		float playerY = DataDictionary.global._float("playerY");
		
		// Add minions around player
		if (ListLinker.count("Minion") < data._float("MINIONS_MAX_COUNT")) {
			float x, y;
			int i = RandomData.nextInt(data._float("MINIONS_MAX_X_DISTANCE").intValue());
			int j = RandomData.nextInt(data._float("MINIONS_MAX_Y_DISTANCE").intValue());
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
			data.setFloat("x", x);
			data.setFloat("y", y);
			
			new Minion(data);
		}
	}
}
