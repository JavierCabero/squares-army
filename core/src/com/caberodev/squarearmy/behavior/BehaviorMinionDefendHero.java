package com.caberodev.squarearmy.behavior;

import java.util.Random;

import com.caberodev.squarearmy.world.Hero;
import com.caberodev.squarearmy.world.Minion;
import com.caberodev.squarearmy.world.World;

public class BehaviorMinionDefendHero implements IBehavior {

	/*
	 * The specific maximum delay this minion will wait (calculated on
	 * constructor)
	 */
	private final int TOP_MOVEMENT_DELAY;
	private static final int MAX_MOVEMENT_DELAY = 25;
	private static final int MAX_MOVEMENT_DISTANCE = 10;
	private static final int BEHAVIOR_TIME = 60;

	/* Variables */
	private Hero hero;
	private Minion minion;
	private int delayUntilNextMove;
	private Random r;
	private int remainingTime = BEHAVIOR_TIME;

	public BehaviorMinionDefendHero(Hero hero, Minion target) {
		this.hero = hero;
		this.minion = target;

		r = new Random();
		TOP_MOVEMENT_DELAY = 35 + r.nextInt(MAX_MOVEMENT_DELAY);
	}

	public void update() {

		/* Waiting a while */
		if (delayUntilNextMove > 0) {
			delayUntilNextMove--;
		} else {
			/* Random move to the hero */
			float newDx, newDy, xDistance, yDistance;

			xDistance = hero.x - minion.x;
			yDistance = hero.y - minion.y;

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
			minion.dx = newDx;
			minion.dy = newDy;

			/* Random wait */
			delayUntilNextMove = r.nextInt(TOP_MOVEMENT_DELAY / 4);
		}

		/* Attack other minions and heroes */
		attack();

		/* Check if the time is less than 0 */
		remainingTime--;
		if (remainingTime <= 0) {
			minion.setBehavior(new BehaviorMinionFollowHero(hero, minion));
		}
	}

	private void attack() {
		boolean usedAttack = false;

		/* Attack heroes */
		for (Hero h : hero.getWorld().getHeroes()) {
			if (usedAttack)
				break;

			/* If the hero is an enemy hero (Because now it is death-match) */
			if (!h.equals(hero)) {
				/* For each minion we calculate if it is close enough to attack */
				float xDistance = h.x - minion.x;
				float yDistance = h.y - minion.y;
				Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

				/* Check distance */
				if (realDistance < minion.getAttackDistance()) {
					usedAttack = true;
					// Use messages
//					h.damage(minion, minion.getAttackDamage());
					break;
				}
			}
		}

		/* If we didn't attack any hero then we attack minions */
		for (Hero h : hero.getWorld().getHeroes()) {

			if (usedAttack)
				break;

			/* If the hero is an enemy hero (Because now it is death-match) */
			if (!h.equals(hero)) {
				/* For each minion we calculate if it is close enough to attack */
				for (Minion enemyMinion : h.getMinions()) {
					float xDistance = enemyMinion.x - minion.x;
					float yDistance = enemyMinion.y - minion.y;
					Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

					/* Check distance */
					if (realDistance < minion.getAttackDistance()) {
						usedAttack = true;
						enemyMinion.damage(minion.getAttackDamage());
						break;
					} else if(realDistance > World.CUT_DISTANCE){
						break;
					}
				}
			}
		}

	}
}
