package com.caberodev.squarearmy.components.behavior;

import java.util.Random;

import com.caberodev.squarearmy.core.WorldObject;
import com.caberodev.squarearmy.util.DataDictionary;
import com.caberodev.squarearmy.util.ListLinker;

public class BehaviorMinionDefendHero extends Behavior {

	/*
	 * The specific maximum delay this minion will wait (calculated on
	 * constructor)
	 */
	private final int TOP_MOVEMENT_DELAY;
	private static final int MAX_MOVEMENT_DELAY = 25;
	private static final int MAX_MOVEMENT_DISTANCE = 10;
	private static final int BEHAVIOR_TIME = 60;

	/* Variables */
	private WorldObject target;
	private int delayUntilNextMove;
	private Random r;
	private int remainingTime = BEHAVIOR_TIME;

	public BehaviorMinionDefendHero(WorldObject target) {
		this.target = target;

		r = new Random();
		TOP_MOVEMENT_DELAY = 35 + r.nextInt(MAX_MOVEMENT_DELAY);
	}

	@Override
	public void think(float delta) {

		/* Waiting a while */
		if (delayUntilNextMove > 0) {
			delayUntilNextMove--;
		} else {
			/* Random move to the hero */
			float newDx, newDy, xDistance, yDistance;

			xDistance = father.data._float("x") - target.data._float("x");
			yDistance = father.data._float("y") - target.data._float("y");

			if (Math.abs(xDistance) > MAX_MOVEMENT_DISTANCE) {
				newDx = MAX_MOVEMENT_DISTANCE;
			} else {
				newDx = r.nextInt(MAX_MOVEMENT_DISTANCE);
			}

			if (Math.abs(yDistance) > MAX_MOVEMENT_DISTANCE) {
				newDy = MAX_MOVEMENT_DISTANCE;
			} else {
				newDy = r.nextInt(MAX_MOVEMENT_DISTANCE);
			}

			if (xDistance < 0) {
				newDx *= -1;
			}
			if (yDistance < 0) {
				newDy *= -1;
			}
			father.data.set("dx", newDx);
			father.data.set("dy", newDy);

			/* Random wait */
			delayUntilNextMove = r.nextInt(TOP_MOVEMENT_DELAY / 4);
		}

		/* Attack other minions and heroes */
		attack();

		/* Check if the time is less than 0 */
		remainingTime--;
		if (remainingTime <= 0) {
			DataDictionary message = new DataDictionary();
			message.set("name", "setBehavior");
			message.set("behavior", "behaviorMinionFollowHero");
			message.set("target", target);
			father.hear(null, message);
		}
	}

	private void attack() {
		boolean usedAttack = false;

		// attack heroes
		for (WorldObject hero : ListLinker.get("heroes")) {
			if (usedAttack)
				break;
			if (hero.equals(target))
				continue;

			float xDistance = hero.data._float("x") - father.data._float("x");
			float yDistance = hero.data._float("x") - father.data._float("x");
			Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

			// if close -> damage
			if (realDistance < father.data._float("attack_distance")) {
				usedAttack = true;
				DataDictionary message = new DataDictionary();
				message.set("name", "damage");
				message.set("amount", father.data._float("attack_damage"));
				hero.hear(null, message);				
				break;
			}
		}

		/* If we didn't attack any hero then we attack minions */
		for (WorldObject hero : ListLinker.get("heroes")) {
			if (usedAttack)
				break;
			if (hero.equals(target))
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
					message.set("name", "damage");
					message.set("amount", father.data._float("attack_damage"));
					enemyMinion.hear(null, message);	
					break;
				}
			}
		}
	}
}
