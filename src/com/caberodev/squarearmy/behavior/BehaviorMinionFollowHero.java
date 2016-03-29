package com.caberodev.squarearmy.behavior;

import java.util.Random;

import com.caberodev.squarearmy.core.WorldObject;
import com.caberodev.squarearmy.util.DataDictionary;
import com.caberodev.squarearmy.util.ListLinker;

public class BehaviorMinionFollowHero extends Behavior {

	private Random r;
	private int delayUntilNextMove;
	private WorldObject target;

	/*
	 * The specific maximum delay this minion will wait (calculated on
	 * constructor)
	 */
	private final int TOP_MOVEMENT_DELAY;

	/* Movement max will be between 500 and 1000 */
	private static final int MAX_MOVEMENT_DELAY = 25;
	private static final int MAX_MOVEMENT_DISTANCE = 10;

	public BehaviorMinionFollowHero(WorldObject target) {
		this.target = target;

		// set same color as hero
		father.data.set("color", target.data.get("color"));

		r = new Random();
		TOP_MOVEMENT_DELAY = 35 + r.nextInt(MAX_MOVEMENT_DELAY);
	}

	public void update() {

		/* Attack other minions */
		attack();

		/* Waiting a while */
		if (delayUntilNextMove > 0) {
			delayUntilNextMove--;
		} else {
			/* Random move to the hero */
			float xDistance, yDistance;

			xDistance = target.data._float("x") - father.data._float("x");
			yDistance = target.data._float("y") - father.data._float("y");

			if (Math.sqrt(xDistance * xDistance + yDistance * yDistance) > MAX_MOVEMENT_DISTANCE * 5) {

				float newDx, newDy;

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
				delayUntilNextMove = r.nextInt(TOP_MOVEMENT_DELAY);
			}
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
