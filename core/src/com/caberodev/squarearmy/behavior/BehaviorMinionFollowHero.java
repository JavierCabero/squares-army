package com.caberodev.squarearmy.behavior;

import java.util.Random;

import com.caberodev.squarearmy.main.Hero;
import com.caberodev.squarearmy.main.Minion;
import com.caberodev.squarearmy.main.World;

public class BehaviorMinionFollowHero implements IBehavior {

	private Random r;
	private int delayUntilNextMove;
	private Minion minion;
	private Hero hero;

	/*
	 * The specific maximum delay this minion will wait (calculated on
	 * constructor)
	 */
	private final int TOP_MOVEMENT_DELAY;

	/* Movement max will be between 500 and 1000 */
	private static final int MAX_MOVEMENT_DELAY = 25;
	private static final int MAX_MOVEMENT_DISTANCE = 10;

	public BehaviorMinionFollowHero(Hero hero, Minion m) {
		this.minion = m;
		this.hero = hero;

		/* Hero Color */
		m.setColor(hero.getColor());

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

			xDistance = hero.getX() - minion.getX();
			yDistance = hero.getY() - minion.getY();

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
				minion.setDx(newDx);
				minion.setDy(newDy);

				/* Random wait */
				delayUntilNextMove = r.nextInt(TOP_MOVEMENT_DELAY);
			}
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
				float xDistance = h.getX() - minion.getX();
				float yDistance = h.getY() - minion.getY();
				Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

				/* Check distance */
				if (realDistance < minion.getAttackDistance()) {
					usedAttack = true;
					h.damage(minion, minion.getAttackDamage());
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
					float xDistance = enemyMinion.getX() - minion.getX();
					float yDistance = enemyMinion.getY() - minion.getY();
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
