package com.caberodev.squarearmy.behavior;

import com.caberodev.squarearmy.world.Hero;
import com.caberodev.squarearmy.world.Minion;
import com.caberodev.squarearmy.world.World;
import com.caberodev.squarearmy.world.WorldObject;

public class BehaviorHeroRunFrom implements IBehavior {

	private Hero hero;
	private WorldObject enemy;

	public BehaviorHeroRunFrom(Hero hero, WorldObject enemyHero) {
		this.hero = hero;
		this.enemy = enemyHero;
	}

	public void update() {
		/* Attack */
		attack();

		/* Check security */
		Double distance = Double.MAX_VALUE;
		Hero enemyHero = null;

		for (Hero h : hero.getWorld().getHeroes()) {
			if (h.equals(hero) || h.equals(enemyHero))
				continue;

			float xDistance = h.x - hero.x;
			float yDistance = h.y - hero.y;
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
			float xDistance = enemyHero.x - hero.x;
			float yDistance = enemyHero.y - hero.y;
			Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);
			if (realDistance > hero.getSightDistance()) {
				hero.setBehavior(new BehaviorHeroCollectMinions(hero), false);
			}
		}

		/* Capture Minions */

		hero.getWorld().requestMinionsFor(hero);

		/* Run to a secure distance */
		float xDistance = hero.x - enemy.x;
		float yDistance = hero.y - enemy.y;

		if (xDistance > 0) {
			hero.dx = hero.getMovementspeed();
		} else {
			hero.dx = -hero.getMovementspeed();
		}
		if (yDistance > 0) {
			hero.dy = hero.getMovementspeed();
		} else {
			hero.dy = -hero.getMovementspeed();

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
				float xDistance = h.x - hero.x;
				float yDistance = h.y - hero.y;
				Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

				/* Check distance */
				if (realDistance < hero.getAttackDistance()) {
					usedAttack = true;
					// Use Messages
//					h.damage(hero, hero.getAttackDamage());
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
					float xDistance = enemyMinion.x - hero.x;
					float yDistance = enemyMinion.y - hero.y;
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
