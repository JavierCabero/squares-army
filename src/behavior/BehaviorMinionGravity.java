package behavior;

import main.Hero;
import main.Minion;
import main.World;

public class BehaviorMinionGravity implements IBehavior {

	/* Constants */
	private static final int GRAVITY_ATTACK_BONUS = 5;

	/* Variables */
	private Hero hero;
	private Minion minion;

	public BehaviorMinionGravity(Hero hero, Minion minion) {
		this.minion = minion;
		this.hero = hero;
	}

	public void update() {
		/* Attack other minions */
		attack();

		/* Random move to the hero */
		float xDistance, yDistance;

		xDistance = hero.getX() - minion.getX();
		yDistance = hero.getY() - minion.getY();

		float newDx, newDy;
		newDx = xDistance;

		newDy = yDistance;

		minion.setDx(minion.getDx() + newDx / 30);
		minion.setDy(minion.getDy() + newDy / 30);
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
					h.damage(minion, minion.getAttackDamage() + GRAVITY_ATTACK_BONUS);
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
						enemyMinion.damage(minion.getAttackDamage() + GRAVITY_ATTACK_BONUS);
						break;
					} else if(realDistance < World.CUT_DISTANCE){
						break;
					}
				}
			}
		}

	}

}
