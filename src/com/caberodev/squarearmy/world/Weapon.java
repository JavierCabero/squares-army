package com.caberodev.squarearmy.world;

import java.util.LinkedList;

import com.caberodev.squarearmy.Hearer;
import com.caberodev.squarearmy.util.DataDictionary;
import com.caberodev.squarearmy.util.ListLinker;

/** 
 * 
 *  @author Javier Cabero Guerra <br>
 * 
 * Copyright 2015 (c) All Rights Reserved. <br><br>
 * 
 */
public class Weapon extends Component {

	public Weapon(WorldObject father) {
		super("weapon", father);
	}
	
	@Override
	public void think(float delta) {
		damageNearbyUnits();
	}
	
	@SuppressWarnings("unchecked")
	private void damageNearbyUnits() {

		// Attack nearby heroes
		for (WorldObject hero : ListLinker.get("heroes")) {
			if (!hero.equals(father)) {
				float xDistance = hero.data._float("x") - father.data._float("x");
				float yDistance = hero.data._float("x") - father.data._float("x");
				Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

				// Check proximity
				if (realDistance < father.data._float("weapon_attack_distance")) {
					LinkedList<Hearer> sources = new LinkedList<Hearer>();
					sources.add(father);
					DataDictionary message = new DataDictionary();
					message.set("name", "damage");
					message.set("dp", father.data._float("dp"));
					hero.hear(sources, message);
					return;
				}
			}
		}

		// Attack nearby minions
		for (WorldObject hero : ListLinker.get("heroes")) {
			if (!hero.equals(hero)) {
				for (WorldObject minion : ((Iterable<WorldObject>) hero.data.get("minion"))) {
					float xDistance = minion.data._float("x") - father.data._float("x");
					float yDistance = minion.data._float("x") - father.data._float("x");
					Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

					/* Check distance */
					if (realDistance < father.data._float("weapon_attack_distance")) {
						LinkedList<Hearer> sources = new LinkedList<Hearer>();
						sources.add(father);
						DataDictionary message = new DataDictionary();
						message.set("name", "damage");
						message.set("dp", father.data._float("dp"));
						minion.hear(sources, message);
						return;
					} else if (realDistance > World.CUT_DISTANCE) {
						break;
					}
				}
			}
		}
	}
}
