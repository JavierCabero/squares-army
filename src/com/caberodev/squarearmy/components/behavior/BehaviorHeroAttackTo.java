package com.caberodev.squarearmy.components.behavior;

import com.caberodev.squarearmy.util.DataDictionary;
import com.caberodev.squarearmy.util.ListLinker;
import com.caberodev.squarearmy.worldobjects.WorldObject;

public class BehaviorHeroAttackTo extends Behavior {

	private static final int ATTACK_TIME = 100;
	private int time_remaining = ATTACK_TIME;

	private WorldObject target;
	
	@Override
	public void think(float delta) {
		
		checkRemainingAttackTime();

		checkTheRestOfHeroes();
		
		checkTargetProximity();
		
		pursueTarget();
	}

	private void checkRemainingAttackTime() {
		/* We don't attack forever */
		if (time_remaining > 0) {
			time_remaining--;
		} else {
			// Start attacking 
			DataDictionary message = new DataDictionary();
			message.setString("name", "setBehavior");
			message.setString("behavior", "behaviorHeroCollectMinions");
			father.hear(null, message);
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

	private void pursueTarget() {
		// Pursue target
		float xDistance = target.data._float("x") - father.data._float("x");
		float yDistance = target.data._float("y") - father.data._float("y");

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
