package com.caberodev.squarearmy.worldobjects;

import java.util.ArrayList;

import com.caberodev.squarearmy.components.graphics.SquareDrawer;
import com.caberodev.squarearmy.components.misc.MinionCollector;
import com.caberodev.squarearmy.components.stats.Health;
import com.caberodev.squarearmy.components.weapons.WeaponMinionKiller;
import com.caberodev.squarearmy.components.weapons.WeaponStandard;
import com.caberodev.squarearmy.util.Color;
import com.caberodev.squarearmy.util.DataDictionary;
import com.caberodev.squarearmy.util.HeroController;

public class Player extends WorldObject {

	public Player() {
		
		data.setFloat("size", 8f);
		data.setColor("color", Color.random());
		data.setFloat("movement_speed", 4f);
		data.setFloat("call_distance", 35f);
		data.setFloat("sight_distance", 300f);
		data.setFloat("close_distance", 50f);
		data.setFloat("behavior_delay", 40f);
		
		Health health = new Health();
		health.data.setFloat("hp", 100f);
		health.addResponse("damage", new ArrayList<DataDictionary>());
		addComponent(health);
		
		WeaponMinionKiller weapon = new WeaponMinionKiller();
//		weapon.data.setFloat("weapon_attack_distance", 50f);
//		weapon.data.setFloat("weapon_attack_damage", 5);
		addComponent(weapon);
		
		addComponent(new SquareDrawer());
		addComponent(new MinionCollector());
		addComponent(new HeroController());
	}
}
