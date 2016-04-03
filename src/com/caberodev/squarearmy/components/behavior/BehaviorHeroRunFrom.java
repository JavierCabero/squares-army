package com.caberodev.squarearmy.components.behavior;

import com.caberodev.squarearmy.util.DataDictionary;
import com.caberodev.squarearmy.util.ListLinker;
import com.caberodev.squarearmy.worldobjects.WorldObject;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 * Copyright 2015 (c) All Rights Reserved. <br><br>
 * 
 * Flee behavior.
 */
public class BehaviorHeroRunFrom extends Behavior {

	private WorldObject target;

	public BehaviorHeroRunFrom(WorldObject target) {
		this.target = target;
	}

	@Override
	public void think(float delta) {

		attack();
		
		checkTheRestOfHeroes();
		
		checkTargetProximity();

		convertNearbyMinions();

		runToSecureDistance();
	}
	
	private void attack() {
		boolean usedAttack = false;

		// attack heroes
		for (WorldObject hero : ListLinker.get("heroes")) {
			if (usedAttack)
				break;
			if (hero.equals(father))
				continue;

			float xDistance = hero.data._float("x") - father.data._float("x");
			float yDistance = hero.data._float("x") - father.data._float("x");
			Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

			// if close -> damage
			if (realDistance < father.data._float("attack_distance")) {
				usedAttack = true;
				DataDictionary message = new DataDictionary();
				message.setString("name", "damage");
				message.setFloat("amount", father.data._float("attack_damage"));
				hero.hear(null, message);				
				break;
			}
		}

		/* If we didn't attack any hero then we attack minions */
		for (WorldObject hero : ListLinker.get("heroes")) {
			if (usedAttack)
				break;
			if (hero.equals(father))
				continue;

			/* For each minion we calculate if it is close enough to attack */
			for (WorldObject enemyMinion : ListLinker.get(hero.data._string("id")+"_minions")) {
				float xDistance = enemyMinion.data._float("x") - father.data._float("x");
				float yDistance = enemyMinion.data._float("y") - father.data._float("y");
				Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

				/* Check distance */
				if (realDistance < father.data._float("attack_distance")) {
					usedAttack = true;
					DataDictionary message = new DataDictionary();
					message.setString("name", "damage");
					message.setFloat("amount", father.data._float("attack_damage"));
					enemyMinion.hear(null, message);	
					break;
				}
			}
		}
	}
	
	private void checkTheRestOfHeroes() {
		// Check safety
		Double distance = Double.MAX_VALUE;
		WorldObject closestHero = null;

		for (WorldObject hero : ListLinker.get("heroes")) {
			if (hero.equals(father) || hero.equals(target))
				continue;

			float xDistance = hero.data._float("x") - father.data._float("x");
			float yDistance = hero.data._float("x") - father.data._float("x");
			Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);
			if (realDistance < distance) {
				distance  = realDistance;
				closestHero = hero;
			}
		}

		// Check proximity of the rest of heroes
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
	
	private void checkTargetProximity() {
		// If the target went too far forget about it and start collecting minions
		float xDistance = target.data._float("x") - father.data._float("x");
		float yDistance = target.data._float("x") - father.data._float("x");
		Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);
		if (realDistance > father.data._float("sight_distance")) {
			DataDictionary message = new DataDictionary();
			message.setString("name", "setBehavior");
			message.setString("behavior", "behaviorHeroCollectMinions");
			father.hear(null, message);
		}
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

	private void runToSecureDistance() {
		/* Run to a secure distance */
		float xDistance = father.data._float("x") - target.data._float("x");
		float yDistance = father.data._float("y") - target.data._float("y");

		if (xDistance > 0) {
			father.data.setFloat("dx", father.data._float("movement_speed"));
		} else {
			father.data.setFloat("dx",-father.data._float("movement_speed"));
		}
		if (yDistance > 0) {
			father.data.setFloat("dy", father.data._float("movement_speed"));
		} else {
			father.data.setFloat("dy",-father.data._float("movement_speed"));
		}
	}
}
