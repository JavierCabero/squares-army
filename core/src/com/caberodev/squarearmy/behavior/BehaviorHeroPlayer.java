package com.caberodev.squarearmy.behavior;

import com.caberodev.squarearmy.InputEngine;
import com.caberodev.squarearmy.world.Hero;
import com.caberodev.squarearmy.world.Minion;
import com.caberodev.squarearmy.world.World;

public class BehaviorHeroPlayer implements IBehavior {
	
	public static Hero player;
		
	@SuppressWarnings("static-access")
	public BehaviorHeroPlayer(Hero player) {
		this.player = player;
	}
	
	private float move_speed = 15f;

	@Override
	public void update() {
		attack();

		// Read keyboard 
		readInput();
	}

	private void attack() {
		boolean usedAttack = false;

		/* Attack heroes */
		for (Hero h : player.getWorld().getHeroes()) {
			if (usedAttack)
				break;

			/* If the hero is an enemy hero (Because now it is death-match) */
			if (!h.equals(this)) {
				/* For each minion we calculate if it is close enough to attack */
				float xDistance = h.x - player.x;
				float yDistance = h.y - player.y;
				Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

				/* Check distance */
				if (realDistance < player.getAttackDistance()) {
					usedAttack = true;
					// TODO: Use messages
//					h.damage(this, getAttackDamage());
					break;
				}
			}
		}

		/* If we didn't attack any hero then we attack minions */
		for (Hero h : player.getWorld().getHeroes()) {

			if (usedAttack)
				break;

			/* If the hero is an enemy hero (Because now it is death-match) */
			if (!h.equals(this)) {
				/* For each minion we calculate if it is close enough to attack */
				for (Minion enemyMinion : h.getMinions()) {
					float xDistance = enemyMinion.x - player.x;
					float yDistance = enemyMinion.y - player.y;
					Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

					/* Check distance */
					if (realDistance < player.getAttackDistance()) {
						usedAttack = true;
						enemyMinion.damage(player.getAttackDamage());
						break;
					} else if (realDistance > World.CUT_DISTANCE) {
						break;
					}
				}
			}
		}

	}
	

	private void readInput() {

		player.dx =  InputEngine.dir.x * move_speed;
		player.dy =  InputEngine.dir.y * move_speed;

		player.world.requestMinionsFor(player);
		player.numMinions = player.minions.size();
		
		/*
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			dx = -movement_speed;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			dx = movement_speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			dy = movement_speed;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			dy = -movement_speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
			if (!minionAbility) {
				for (Minion m : minions) {
					m.setBehavior(new BehaviorMinionDontMove(this, m));
				}
				minionAbility = true;
			}
		} else if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
			if (!minionAbility) {
				for (Minion m : minions) {
					m.setBehavior(new BehaviorMinionGravity(this, m));
				}
				minionAbility = true;
			}
		} else if (minionAbility) {
			// Reset default behavior 
			minionAbility = false;
			for (Minion m : minions) {
				m.setBehavior(new BehaviorMinionFollowHero(this, m));
			}
		}
		*/
	}
}
