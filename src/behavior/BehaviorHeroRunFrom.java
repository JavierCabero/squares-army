package behavior;

import entity.Entity;
import main.Hero;
import main.Minion;
import main.World;

public class BehaviorHeroRunFrom implements IBehavior {

	private Hero hero;
	private Entity enemy;

	public BehaviorHeroRunFrom(Hero hero, Entity enemyHero) {
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
			float xDistance = enemyHero.getX() - hero.getX();
			float yDistance = enemyHero.getY() - hero.getY();
			Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);
			if (realDistance > hero.getSightDistance()) {
				hero.setBehavior(new BehaviorHeroCollectMinions(hero), false);
			}
		}

		/* Capture Minions */

		hero.getWorld().requestMinionsFor(hero);

		/* Run to a secure distance */
		float xDistance = hero.getX() - enemy.getX();
		float yDistance = hero.getY() - enemy.getY();

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
