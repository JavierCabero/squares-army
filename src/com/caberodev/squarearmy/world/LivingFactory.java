package com.caberodev.squarearmy.world;

import java.util.ArrayList;
import java.util.HashMap;

import com.caberodev.squarearmy.appearance.SquareDrawer;
import com.caberodev.squarearmy.behavior.BehaviorHeroCollectMinions;
import com.caberodev.squarearmy.behavior.BehaviorMinionNoHero;
import com.caberodev.squarearmy.util.Color;
import com.caberodev.squarearmy.util.DataDictionary;
import com.caberodev.squarearmy.util.HeroController;
import com.caberodev.squarearmy.util.ListLinker;

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
			wo.data.set("size", 3);
			
			HashMap<String, Component> cmps = new HashMap<String, Component>();
			
			// Health
			Component health = new Health(wo);
			health.data.set("hp", 10);
			health.responses.put("damage", new ArrayList<DataDictionary>()); // TODO:
			// Damage
			Component weapon = new Weapon(wo);
			weapon.data.set("weapon_attack_distance", 50f);
			weapon.data.set("weapon_attack_damage", 1);
			// Behavior
			Component behavior = new BehaviorMinionNoHero(wo);
			// Appearance
			Component drawer = new SquareDrawer(wo);
			
			// Set father
			health.father   = wo;
			weapon.father   = wo;
			behavior.father = wo;
			drawer.father   = wo;
			
			// Save children together
			cmps.put("health",   health);
			cmps.put("weapon",   weapon);
			cmps.put("behavior", behavior);
			cmps.put("drawer",   drawer);
			
			// Set children in father
			wo.components = cmps;
			
			// Add to ListLinker
			ListLinker.add("minions", wo);
			ListLinker.add("minions_neutral", wo);
			
			return wo;
		}
		case "hero":
		{	
			WorldObject wo = new WorldObject(data);
			wo.data.set("size", 5);
			wo.data.set("color", Color.random());
			wo.data.set("movement_speed", 4);
			wo.data.set("call_distance", 35);
			wo.data.set("sight_distance", 300);
			wo.data.set("close_distance", 50);
			wo.data.set("behavior_delay", 40);
			
			HashMap<String, Component> cmps = new HashMap<String, Component>();
			
			// Health
			Component health = new Health(wo);
			health.data.set("hp", 100);
			health.responses.put("damage", new ArrayList<DataDictionary>()); // TODO:
			// Damage
			Component weapon = new Weapon(wo);
			weapon.data.set("weapon_attack_distance", 50f);
			weapon.data.set("weapon_attack_damage", 5);
			Component behavior = new BehaviorHeroCollectMinions(wo);
			// Appearance
			Component drawer = new SquareDrawer(wo);
			
			Component minionCollector = new MinionCollector(wo);
			
			// Save children together
			cmps.put("health",   health);
			cmps.put("weapon",   weapon);
			cmps.put("behavior", behavior);
			cmps.put("drawer",   drawer);
			cmps.put("minionCollector", minionCollector);
			
			// Set children in father
			wo.components = cmps;
			
			// Add to ListLinker
			ListLinker.add("heroes", wo);
			
			return wo;
		}
		case "player":
		{	
			WorldObject wo = new WorldObject(data);
			wo.data.set("size", 5);
			wo.data.set("color", Color.random());
			wo.data.set("movement_speed", 2);
			wo.data.set("call_distance", 35);
			wo.data.set("sight_distance", 300);
			wo.data.set("close_distance", 50);
			wo.data.set("behavior_delay", 40);
			
			HashMap<String, Component> cmps = new HashMap<String, Component>();
			
			// Health
			Component health = new Health(wo);
			health.data.set("hp", 100);
			health.responses.put("damage", new ArrayList<DataDictionary>()); 
			// Damage
			Component weapon = new Weapon(wo);
			weapon.data.set("weapon_attack_distance", 50f);
			weapon.data.set("weapon_attack_damage", 5);
			// Appearance
			Component drawer = new SquareDrawer(wo);
			// Minion Collector
			Component minionCollector = new MinionCollector(wo);
			// Player position
			Component heroController = new HeroController(wo); 
			
			// Save children together
			cmps.put("health",   health);
			cmps.put("weapon",   weapon);
			cmps.put("drawer",   drawer);
			cmps.put("minionCollector", minionCollector);
			cmps.put("heroController", heroController);
			// Set children in father
			wo.components = cmps;
			
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
