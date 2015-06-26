package com.caberodev.squarearmy.behavior;

import com.caberodev.squarearmy.world.Hero;
import com.caberodev.squarearmy.world.Minion;
import com.caberodev.squarearmy.world.World;

public class BehaviorHeroAttackTo implements IBehavior {

	/* Constants */
	private static final int ATTACK_TIME = 100;

	private Hero hero;
	private Hero hero_enemy;
	private int time_remaining = ATTACK_TIME;

	public BehaviorHeroAttackTo(Hero hero, Hero enemyHero) {
		this.hero = hero;
		this.hero_enemy = enemyHero;
	}

	public void update() {
		/* We don't attack forever */
		if (time_remaining > 0) {
			time_remaining--;
		} else {
			hero.setBehavior(new BehaviorHeroCollectMinions(hero), false);
		}
		/* Attack */
		attack();

		/* Check security */
		Double distance = Double.MAX_VALUE;
		Hero enemyHero = null;

		for (Hero h : hero.getWorld().getHeroes()) {
			if (h.equals(hero) || h.equals(hero_enemy))
				continue;

			float xDistance = h.getX() - hero.getX();
			float yDistance = h.getY() - hero.getY();
			Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);
			if (realDistance < distance) {
				distance = realDistance;
				enemyHero = h;
			}
		}

		if (enemyHero != null) {

			if (distance < hero.getSightDistance()) {
				if (enemyHero.getNumMinions() < hero.getNumMinions()) {
					/* Attack */
					hero.setBehavior(new BehaviorHeroAttackTo(hero, enemyHero), false);
				} else {
					/* Run */
					hero.setBehavior(new BehaviorHeroRunFrom(hero, enemyHero), false);
				}
			}
		}
		if (true) { /* To reuse the name of the variables */
			float xDistance = hero_enemy.getX() - hero.getX();
			float yDistance = hero_enemy.getY() - hero.getY();
			Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);
			if (realDistance > hero.getSightDistance()) {
				hero.setBehavior(new BehaviorHeroCollectMinions(hero), false);
			}
		}

		/* Capture Minions */

		hero.getWorld().requestMinionsFor(hero);

		/* Run to a secure distance */
		float xDistance = hero_enemy.getX() - hero.getX();
		float yDistance = hero_enemy.getY() - hero.getY();

		if (xDistance > 0) {
			hero.setDx(hero.getMovementspeed());
		} else {
			hero.setDx(-hero.getMovementspeed());
		}
		if (yDistance > 0) {
			hero.setDy(hero.getMovementspeed());
		} else {
			hero.setDy(-hero.getMovementspeed());

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
				float xDistance = h.getX() - hero.getX();
				float yDistance = h.getY() - hero.getY();
				Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

				/* Check distance */
				if (realDistance < hero.getAttackDistance()) {
					usedAttack = true;
					h.damage(hero, hero.getAttackDamage());
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
					float xDistance = enemyMinion.getX() - hero.getX();
					float yDistance = enemyMinion.getY() - hero.getY();
					Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

					/* Check distance */
					if (realDistance < hero.getAttackDistance()) {
						usedAttack = true;
						enemyMinion.damage(hero.getAttackDamage());
						break;
					} else if (realDistance > World.CUT_DISTANCE) {
						break;
					}
				}
			}
		}

	}

}
