package com.caberodev.squarearmy.core;

import java.util.ArrayList;
import java.util.HashMap;

import com.caberodev.squarearmy.appearance.SquareDrawer;
import com.caberodev.squarearmy.behavior.BehaviorHeroCollectMinions;
import com.caberodev.squarearmy.behavior.BehaviorMinionNoHero;
import com.caberodev.squarearmy.components.Component;
import com.caberodev.squarearmy.components.Health;
import com.caberodev.squarearmy.util.Color;
import com.caberodev.squarearmy.util.DataDictionary;
import com.caberodev.squarearmy.util.HeroController;
import com.caberodev.squarearmy.util.ListLinker;
import com.caberodev.squarearmy.world.MinionCollector;
import com.caberodev.squarearmy.world.Weapon;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 */
public class LivingFactory {

	public static WorldObject create(String name, DataDictionary data) {
		
		switch(name) {
		case "minion":
		{ 
			WorldObject wo = new WorldObject(data);
			wo.data.set("class", "minion");
			wo.data.set("size", 5);
			
			wo.addComponent(new Health());
			
			Weapon weapon = new Weapon();
			weapon.data.set("weapon_attack_distance", 50f);
			weapon.data.set("weapon_attack_damage", 1);
			wo.addComponent(weapon);
			
			wo.addComponent(new BehaviorMinionNoHero());
			
			wo.addComponent(new SquareDrawer());
			
			// Add to ListLinker
			ListLinker.add("minions", wo);
			
			return wo;
		}
		case "hero":
		{	
			WorldObject wo = new WorldObject(data);
			wo.data.set("size", 8);
			wo.data.set("color", Color.random());
			wo.data.set("movement_speed", 4);
			wo.data.set("call_distance", 35);
			wo.data.set("sight_distance", 300);
			wo.data.set("close_distance", 50);
			wo.data.set("behavior_delay", 40);
			
			Health health = new Health();
			health.data.set("hp", 100);
			health.addResponse("damage", new ArrayList<DataDictionary>()); // TODO:			
			wo.addComponent(health);
			
			Weapon weapon = new Weapon();
			weapon.data.set("weapon_attack_distance", 50f);
			weapon.data.set("weapon_attack_damage", 5);
			wo.addComponent(weapon);
			
			wo.addComponent(new BehaviorHeroCollectMinions());

			wo.addComponent(new SquareDrawer());
			
			wo.addComponent(new MinionCollector());
			
			// Add to ListLinker
			ListLinker.add("heroes", wo);
			
			return wo;
		}
		case "player":
		{	
			WorldObject wo = new WorldObject(data);
			wo.data.set("size", 8);
			wo.data.set("color", Color.random());
			wo.data.set("movement_speed", 4);
			wo.data.set("call_distance", 35);
			wo.data.set("sight_distance", 300);
			wo.data.set("close_distance", 50);
			wo.data.set("behavior_delay", 40);
			
			Health health = new Health();
			health.data.set("hp", 100);
			health.addResponse("damage", new ArrayList<DataDictionary>());
			wo.addComponent(health);
			
			Weapon weapon = new Weapon();
			weapon.data.set("weapon_attack_distance", 50f);
			weapon.data.set("weapon_attack_damage", 5);
			wo.addComponent(weapon);
			
			wo.addComponent(new SquareDrawer());

			wo.addComponent(new MinionCollector());
			
			wo.addComponent(new HeroController());
			
			// Add to ListLinker
			ListLinker.add("heroes", wo);
			
			return wo;
		}
		// Scheme not found
		default: 
			System.err.println("Error: Scheme " + name + " not found.");
			return null;
		}
	}
}
