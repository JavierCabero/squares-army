package com.caberodev.squarearmy.behavior;
import com.caberodev.squarearmy.world.Hero;
import com.caberodev.squarearmy.world.Minion;
import com.caberodev.squarearmy.world.World;

public class BehaviorMinionDontMove implements IBehavior {

	private Minion minion;
	private Hero hero;

	public BehaviorMinionDontMove(Hero hero, Minion m) {
		this.minion = m;
		this.hero = hero;
	}

	public void update() {
		attack();
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
