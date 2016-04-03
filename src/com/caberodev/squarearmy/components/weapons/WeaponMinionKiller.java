package com.caberodev.squarearmy.components.weapons;

import java.util.LinkedList;

import org.lwjgl.input.Keyboard;

import com.caberodev.squarearmy.core.Hearer;
import com.caberodev.squarearmy.util.DataDictionary;
import com.caberodev.squarearmy.util.ListLinker;
import com.caberodev.squarearmy.worldobjects.WorldObject;

public class WeaponMinionKiller extends Weapon {

	public WeaponMinionKiller() {
		this.data.setFloat("attack_distance", 50f);
		this.data.setFloat("attack_damage",   50f);
	}
	
	@Override
	public void think(float delta) {
//		System.out.println("Looking for minions (" + ListLinker.count("Minion") + ") from (" + father.data._float("x") + ", " + father.data._float("y") + ")");
		for (WorldObject minion : ListLinker.get("Minion")) {
			
			float xDistance = minion.data._float("x") - father.data._float("x");
			float yDistance = minion.data._float("y") - father.data._float("y");
			Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

//			System.out.println("   Saw minion at " + realDistance + "m! (" + minion.data._float("x") + ", " + minion.data._float("y") + ")");
			
			/* Check distance */
			if (realDistance < data._float("attack_distance")) {
				LinkedList<Hearer> sources = new LinkedList<Hearer>();
				sources.add(father);
				DataDictionary message = new DataDictionary();
				message.setString("name", "damage");
				message.setFloat("damage_points", data._float("attack_damage"));
				
				if(Keyboard.isKeyDown(Keyboard.KEY_O) )
					minion.hear(sources, message);
				return;
			}
		}
	}
	
}
