package com.caberodev.squarearmy.components.behavior;
//package com.caberodev.squarearmy.behavior;
//
//import com.caberodev.squarearmy.world.Hero;
//import com.caberodev.squarearmy.world.Minion;
//import com.caberodev.squarearmy.world.World;
//
//public class BehaviorMinionGravity implements Parasite {
//
//	/* Constants */
//	private static final int GRAVITY_ATTACK_BONUS = 5;
//
//	/* Variables */
//	private Hero hero;
//	private Minion minion;
//
//	public BehaviorMinionGravity(Hero hero, Minion minion) {
//		this.minion = minion;
//		this.hero = hero;
//	}
//
//	public void update() {
//		/* Attack other minions */
//		attack();
//
//		/* Random move to the hero */
//		float xDistance, yDistance;
//
//		xDistance = hero.x - minion.x;
//		yDistance = hero.y - minion.y;
//
//		float newDx, newDy;
//		newDx = xDistance;
//
//		newDy = yDistance;
//
//		minion.dx = minion.dx + newDx / 30;
//		minion.dy = minion.dy + newDy / 30;
//	}
//
//	private void attack() {
//		boolean usedAttack = false;
//
//		/* Attack heroes */
//		for (Hero h : hero.getWorld().getHeroes()) {
//			if (usedAttack)
//				break;
//
//			/* If the hero is an enemy hero (Because now it is death-match) */
//			if (!h.equals(hero)) {
//				/* For each minion we calculate if it is close enough to attack */
//				float xDistance = h.x - minion.x;
//				float yDistance = h.y - minion.y;
//				Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);
//
//				/* Check distance */
//				if (realDistance < minion.getAttackDistance()) {
//					usedAttack = true;
//					// TODO: Use Messages
////					h.damage(minion, minion.getAttackDamage() + GRAVITY_ATTACK_BONUS);
//					break;
//				}
//			}
//		}
//
//		/* If we didn't attack any hero then we attack minions */
//		for (Hero h : hero.getWorld().getHeroes()) {
//
//			if (usedAttack)
//				break;
//
//			/* If the hero is an enemy hero (Because now it is death-match) */
//			if (!h.equals(hero)) {
//				/* For each minion we calculate if it is close enough to attack */
//				for (Minion enemyMinion : h.getMinions()) {
//					float xDistance = enemyMinion.x - minion.x;
//					float yDistance = enemyMinion.y - minion.y;
//					Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);
//
//					/* Check distance */
//					if (realDistance < minion.getAttackDistance()) {
//						usedAttack = true;
//						enemyMinion.damage(minion.getAttackDamage() + GRAVITY_ATTACK_BONUS);
//						break;
//					} else if(realDistance < World.CUT_DISTANCE){
//						break;
//					}
//				}
//			}
//		}
//
//	}
//
//}
