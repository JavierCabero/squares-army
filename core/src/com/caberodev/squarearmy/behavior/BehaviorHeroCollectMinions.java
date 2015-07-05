package com.caberodev.squarearmy.behavior;

import com.caberodev.squarearmy.world.Hero;
import com.caberodev.squarearmy.world.Minion;
import com.caberodev.squarearmy.world.World;

public class BehaviorHeroCollectMinions implements IBehavior {

	private Hero hero;

	public BehaviorHeroCollectMinions(Hero hero) {
		this.hero = hero;
	}

	public void update() {
		/* Attack */
		attack();

		/* Check security */
		Double distance = Double.MAX_VALUE;
		Hero enemyHero = null;

		for (Hero h : hero.getWorld().getHeroes()) {
			float xDistance = h.x - hero.x;
			float yDistance = h.y - hero.y;
			Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);
			if (!h.equals(hero) && realDistance < distance) {
				distance = realDistance;
				enemyHero = h;
			}
		}

		if (enemyHero != null) {

			if (distance < hero.getSightDistance()) {
				if (enemyHero.getNumMinions() < hero.getNumMinions()
						&& (hero.getHealth() > hero.getMaxHealth() / 3 || hero.getHealth() > enemyHero.getHealth())) {
					/* Attack */
					hero.setBehavior(new BehaviorHeroAttackTo(hero, enemyHero), false);
				} else {
					/* Run */
					hero.setBehavior(new BehaviorHeroRunFrom(hero, enemyHero), false);
				}
			}
		}

		/* Search for the closest minion */
		Minion closestMinion = null;
		distance = Double.MAX_VALUE;

		hero.getWorld().requestMinionsFor(hero);

		for (Minion m : hero.getWorld().getNeutralMinions()) {
			float xDistance = m.x - hero.x;
			float yDistance = m.y - hero.y;

			Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);
			/* If it is a better target */
			if (realDistance < distance) {
				distance = realDistance;
				closestMinion = m;
			}
		}

		/* If not null calculate move */
		if (closestMinion != null) {
			float xDistance = closestMinion.x - hero.x;
			float yDistance = closestMinion.y - hero.y;

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
					// TODO: Use Messages
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
