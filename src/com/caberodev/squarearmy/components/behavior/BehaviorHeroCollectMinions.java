package com.caberodev.squarearmy.components.behavior;

import com.caberodev.squarearmy.util.DataDictionary;
import com.caberodev.squarearmy.util.ListLinker;
import com.caberodev.squarearmy.worldobjects.WorldObject;

/** 
 * 
 *  @author Javier Cabero Guerra <br>
 * 
 */
public class BehaviorHeroCollectMinions extends Behavior {

	@Override
	public void think(float delta) {
		convertNearbyMinions();
		
		searchForMinions();
		
		checkHeroes();
	}

	private void convertNearbyMinions() {
		for (WorldObject minion : ListLinker.get("minions_neutral")) {
			float xDistance = minion.data._float("x") - father.data._float("x");
			float yDistance = minion.data._float("x") - father.data._float("x");

			Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

			// If the neutral minion is close enough convert it
			if (realDistance < father.data._float("call_distance")) {
				// Start attacking 
				DataDictionary message = new DataDictionary();
				message.setString("name", "setBehavior");
				message.setString("behavior", "behaviorMinionFollowHero");
				message.setWorldObject("target", father);
				minion.hear(null, message); 
			}
		}
	}

	private void searchForMinions() {

		// Closest neutral minion
		Double distance = Double.MAX_VALUE;
		WorldObject closestMinion = null;

		for (WorldObject minion : ListLinker.get("minions_neutral")) {
			float xDistance = minion.data._float("x") - father.data._float("x");
			float yDistance = minion.data._float("x") - father.data._float("x");

			Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);
			/* If it is a better target */
			if (realDistance < distance) {
				distance = realDistance;
				closestMinion = minion;
			}
		}

		// Compute next movement
		if (closestMinion != null) {
			float xDistance = closestMinion.data._float("x") - father.data._float("x");
			float yDistance = closestMinion.data._float("y") - father.data._float("y");

			if (xDistance > 0) {
				father.data.setFloat("dx", +father.data._float("movement_speed"));
			} else {
				father.data.setFloat("dx", -father.data._float("movement_speed"));
			}
			if (yDistance > 0) {
				father.data.setFloat("dy", +father.data._float("movement_speed"));
			} else {
				father.data.setFloat("dy", -father.data._float("movement_speed"));
			}

		}
	}
	
	private void checkHeroes() {
		
		// Closest enemy hero
		Double distance = Double.MAX_VALUE;
		WorldObject closestHero = null;

		for (WorldObject hero : ListLinker.get("heroes")) {
			float xDistance = hero.data._float("x") - father.data._float("x");
			float yDistance = hero.data._float("x") - father.data._float("x");
			Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);
			if (!hero.equals(father) && realDistance < distance) {
				distance = realDistance;
				closestHero = hero;
			}
		}
		
		// If the hero is too close attack or defend
		if (closestHero != null) {
			if (distance < father.data._float("sight_distance")) {
				if (closestHero.data._float("minion_count") < father.data._float("minion_count")
						&& (father.data._float("hp") > father.data._float("max_hp") / 3 || father.data._float("hp") > closestHero.data._float("hp"))) {
	
					// Start attacking 
					DataDictionary message = new DataDictionary();
					message.setString("name", "setBehavior");
					message.setString("behavior", "behaviorHeroAttackTo");
					message.setWorldObject("target", closestHero);
					father.hear(null, message);
				} else {
					
					// Start running away
					DataDictionary message = new DataDictionary();
					message.setString("name", "setBehavior");
					message.setString("behavior", "behaviorHeroRunFrom");
					message.setWorldObject("target", closestHero);
					father.hear(null, message);
				}
			}
		}
	}
}
