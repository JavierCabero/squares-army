package com.caberodev.squarearmy.main;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.caberodev.squarearmy.InputEngine;
import com.caberodev.squarearmy.Vec2;
import com.caberodev.squarearmy.appearance.AppearanceHeroDamaged;
import com.caberodev.squarearmy.appearance.AppearanceSquare;
import com.caberodev.squarearmy.appearance.IRenderable;
import com.caberodev.squarearmy.appearance.IRenderator;
import com.caberodev.squarearmy.attack.BasicAttack;
import com.caberodev.squarearmy.attack.IAttack;
import com.caberodev.squarearmy.behavior.BehaviorHeroAttackTo;
import com.caberodev.squarearmy.behavior.BehaviorHeroCollectMinions;
import com.caberodev.squarearmy.behavior.BehaviorHeroRunFrom;
import com.caberodev.squarearmy.behavior.BehaviorMinionDefendHero;
import com.caberodev.squarearmy.behavior.BehaviorMinionNoHero;
import com.caberodev.squarearmy.behavior.IBehavior;
import com.caberodev.squarearmy.entity.AbstractMoveableEntity;
import com.caberodev.squarearmy.entity.Entity;
import com.caberodev.squarearmy.entity.Color;
import com.caberodev.squarearmy.health.BasicHealth;
import com.caberodev.squarearmy.health.IHealth;

/* Hero
 * 
 * 	This is the main character that you will move.
 * With the hero, you must win all the minions you
 * can in order to make your army bigger.
 */

public class Hero extends AbstractMoveableEntity implements IRenderable {

	/* Constants */
	private static final int MAX_HEALTH = 25;
	private static final int CLOSE_DISTANCE = 50;
	public  static final int EASY = 4;
	public  static final int MEDIUM = 3;
	public  static final int HARD = 2;
	public  static final int IMPOSSIBLE = 1;
	private static final int INITIAL_ATTACK_DAMAGE = 3;
	private static final double INITIAL_ATTACK_DISTANCE = 50.0;
	private static final int BEHAVIOR_DELAY = 40;

	/* Variables */
	private float size = 8;
	private float movement_speed = 4;
	private Color color;
	private float callDistance;
	private World world;
	private boolean isPlayer;
	private int AILevel = IMPOSSIBLE;
	private Random r = new Random();
	private IRenderator appearance;
	private IHealth health;
	private IAttack attack;
	private final float sightDistance = 300f;
	private int behavior_delay = BEHAVIOR_DELAY;

	public static Vec2 player = new Vec2(0, 0);
	
	/* For AI Hero */
	private IBehavior behavior;

	/* Data Structures */
	private Set<Minion> minions;
	private int numMinions;

	public Hero(float x, float y) {
		super(x, y);

		health = new BasicHealth(MAX_HEALTH);
		attack = new BasicAttack(INITIAL_ATTACK_DAMAGE, INITIAL_ATTACK_DISTANCE);
		minions = new HashSet<Minion>();
		numMinions = 0;

		/* Default call distance: 35 */
		callDistance = 35;
		/* Default color: Blue */
		color = Color.BLUE;

		/* Default behavior: Collect minions */
		behavior = new BehaviorHeroCollectMinions(this);

		/* Default appearance : Square appearance */
		appearance = new AppearanceSquare(this);
	}

	public void damage(Entity source, int dmg) {

		/* Notify hero's dead */
		if (!appearance.getClass().equals(AppearanceHeroDamaged.class) && health.damage(dmg)) {
			world.addDeadHero(this);
		} else if (!appearance.getClass().equals(AppearanceHeroDamaged.class)) {
			setAppearance(new AppearanceHeroDamaged(this, getAppearance()));
			if (!behavior.getClass().equals(BehaviorHeroAttackTo.class))
				setBehavior(new BehaviorHeroRunFrom(this, source), true);
		}
	}

	public void setAppearance(IRenderator appearance) {
		this.appearance = appearance;

	}

	public void render() {
		appearance.render();
	}

	public IRenderator getAppearance() {
		return appearance;
	}

	public void setPlayer(boolean isPlayer) {
		this.isPlayer = isPlayer;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	/* This will make the hero move */
	public void update() {

		/* Check if enemy minions are too close so we call minions to defend */
		lookForNearbyEnemyMinions();
		
		if (isPlayer) {
			attack();

			// Read keyboard 
			readInput();
		} else {
			if (r.nextInt() % AILevel == 0)
				behavior.update();
		}

		// Apply movement 
		super.update();

		// Camera follows
		if(isPlayer) {
			player.x = x;
			player.y = y;
		}
		
		// Reduce movement (friction)
		movementReduction();
	}

	private void attack() {
		boolean usedAttack = false;

		/* Attack heroes */
		for (Hero h : getWorld().getHeroes()) {
			if (usedAttack)
				break;

			/* If the hero is an enemy hero (Because now it is death-match) */
			if (!h.equals(this)) {
				/* For each minion we calculate if it is close enough to attack */
				float xDistance = h.getX() - getX();
				float yDistance = h.getY() - getY();
				Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

				/* Check distance */
				if (realDistance < getAttackDistance()) {
					usedAttack = true;
					h.damage(this, getAttackDamage());
					break;
				}
			}
		}

		/* If we didn't attack any hero then we attack minions */
		for (Hero h : getWorld().getHeroes()) {

			if (usedAttack)
				break;

			/* If the hero is an enemy hero (Because now it is death-match) */
			if (!h.equals(this)) {
				/* For each minion we calculate if it is close enough to attack */
				for (Minion enemyMinion : h.getMinions()) {
					float xDistance = enemyMinion.getX() - getX();
					float yDistance = enemyMinion.getY() - getY();
					Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

					/* Check distance */
					if (realDistance < getAttackDistance()) {
						usedAttack = true;
						enemyMinion.damage(getAttackDamage());
						break;
					} else if (realDistance > World.CUT_DISTANCE) {
						break;
					}
				}
			}
		}

	}

	public Set<Minion> getMinions() {
		return minions;
	}

	public void setMinions(Set<Minion> minions) {
		this.minions = minions;
	}

	public void setLevel(int level) {
		this.AILevel = level;
	}

	private float move_speed = 15f;
	
	private void readInput() {

		dx =  InputEngine.dir.x * move_speed;
		dy =  InputEngine.dir.y * move_speed;

		world.requestMinionsFor(this);
		numMinions = minions.size();
		
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

	public void addMinion(Minion m) {
		if (m != null) {
			minions.add(m);
			numMinions++;
		}
	}

	public float getMinionsCallDistance() {
		return callDistance;
	}

	private void movementReduction() {
		/* Movement reduction */
		if (dx > 0) {
			dx--;
			if (dx < 1) {
				dx = 0;
			}
		} else if (dx < 0) {
			dx++;
			if (dx > -1) {
				dx = 0;
			}
		}
		if (dy > 0) {
			dy--;
			if (dy < 1) {
				dy = 0;
			}
		} else if (dy < 0) {
			dy++;
			if (dy > -1) {
				dy = 0;
			}
		}
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public World getWorld() {
		return world;
	}

	public float getMovementspeed() {
		return movement_speed;
	}

	private void lookForNearbyEnemyMinions() {
		boolean aMinionIsTooClose = false;

		for (Hero h : getWorld().getHeroes()) {
			if (aMinionIsTooClose)
				break;
			/* If the hero is an enemy hero (Because now it is death-match) */
			if (!h.equals(this)) {
				/* For each minion we calculate if it is too close */
				for (Minion m : h.getMinions()) {
					float xDistance = m.getX() - getX();
					float yDistance = m.getY() - getY();
					Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);
					/* Check distance */
					if(realDistance < 0){
						System.out.println("THIS IS PURE SHIT");
					}
					if (realDistance > 0 && realDistance < CLOSE_DISTANCE) {
						aMinionIsTooClose = true;
						break;
					}
				}
			}
		}

		/* If such thing tell all our minions to protect us */
		if (aMinionIsTooClose) {
			for (Minion m : getMinions()) {
				/* If they are not awared */
				if (!m.getBehavior().equals(BehaviorMinionDefendHero.class)) {
					m.setBehavior(new BehaviorMinionDefendHero(this, m));
				}
			}
		}

	}

	public float getSize() {
		return size;
	}

	public int getNumMinions() {
		return numMinions;
	}

	public void removeMinion(Minion m) {
		if (minions.remove(m)) {
			numMinions--;
		}
	}

	public void freeMinions() {
		for (Minion m : minions) {
			m.setBehavior(new BehaviorMinionNoHero(m));
			world.freeMinion(m);
		}
	}

	public int getHealth() {
		return health.getHealth();
	}

	public Double getAttackDistance() {
		return attack.getAttackDistance();
	}

	public int getAttackDamage() {
		return attack.getAttackDamage();
	}

	public float getSightDistance() {
		return sightDistance;
	}

	public void setBehavior(IBehavior behavior, boolean forced) {
		if (!forced && behavior_delay > 0) {
			behavior_delay--;
		} else {
			this.behavior = behavior;
			behavior_delay = BEHAVIOR_DELAY;
		}
	}

	public int getMaxHealth() {
		return MAX_HEALTH;
	}

}
