package com.caberodev.squarearmy.components.weapons;

import java.util.LinkedList;

import com.caberodev.squarearmy.core.Hearer;
import com.caberodev.squarearmy.util.DataDictionary;
import com.caberodev.squarearmy.util.ListLinker;
import com.caberodev.squarearmy.worldobjects.WorldObject;

public class WeaponStandard extends Weapon {

	
	public WeaponStandard() {
		data.setFloat("attack_distance", 50f);
		data.setFloat("attack_damage",    1f);	
	}
	
	@Override
	public void think(float delta) {
		damageNearbyUnits();
	}
	
	private void damageNearbyUnits() {

		for (WorldObject hero : ListLinker.get("Hero")) {
			if (!hero.equals(father)) {
				
				// Attack nearby heroes			
				attack_hero(hero);
				
				// Attack nearvy minions
				attack_hero_minions(hero);
			}
		}
	}

	private void attack_hero(WorldObject hero) {
		float xDistance = hero.data._float("x") - father.data._float("x");
		float yDistance = hero.data._float("x") - father.data._float("x");
		Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

		// Check proximity
		if (realDistance < father.data._float("attack_distance")) {
			LinkedList<Hearer> sources = new LinkedList<Hearer>();
			sources.add(father);
			DataDictionary message = new DataDictionary();
			message.setString("name", "damage");
			message.setFloat("damage_points", data._float("attack_damage"));
			hero.hear(sources, message);
			return;
		}
	}

//	@SuppressWarnings("unchecked")
	private void attack_hero_minions(WorldObject hero) {
		// TODO
//		for (WorldObject minion : ((Iterable<WorldObject>) hero.data.get("minions"))) {
//			float xDistance = minion.data._float("x") - father.data._float("x");
//			float yDistance = minion.data._float("x") - father.data._float("x");
//			Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);
//
//			/* Check distance */
//			if (realDistance < father.data._float("attack_distance")) {
//				LinkedList<Hearer> sources = new LinkedList<Hearer>();
//				sources.add(father);
//				DataDictionary message = new DataDictionary();
//				message.setString("name", "damage");
//				message.setFloat("damage_points", father.data._float("attack_damage"));
//				minion.hear(sources, message);
//				return;
//			} else if (realDistance > World.CUT_DISTANCE) {
//				break;
//			}
//		}
	}
}
