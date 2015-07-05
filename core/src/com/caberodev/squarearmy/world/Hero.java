package com.caberodev.squarearmy.world;

import java.util.HashSet;
import java.util.Set;

import com.caberodev.squarearmy.appearance.SquareDrawer;
import com.caberodev.squarearmy.attack.BasicAttack;
import com.caberodev.squarearmy.attack.IAttack;
import com.caberodev.squarearmy.behavior.BehaviorHeroCollectMinions;
import com.caberodev.squarearmy.behavior.BehaviorHeroPlayer;
import com.caberodev.squarearmy.behavior.BehaviorMinionDefendHero;
import com.caberodev.squarearmy.behavior.BehaviorMinionNoHero;
import com.caberodev.squarearmy.behavior.IBehavior;
import com.caberodev.squarearmy.health.BasicHealth;
import com.caberodev.squarearmy.health.IHealth;
import com.caberodev.squarearmy.util.Color;
import com.caberodev.squarearmy.util.RandomData;
import com.sun.xml.internal.stream.Entity;

/* Hero
 * 
 * 	This is the main character that you will move.
 * With the hero, you must win all the minions you
 * can in order to make your army bigger.
 */

public class Hero extends WorldObject {

	/* Constants */
	private static final int MAX_HEALTH = 25;
	private static final int CLOSE_DISTANCE = 50; 
	public  static final int EASY = 4;
	public  static final int MEDIUM = 3;
	public  static final int HARD = 2;
	public  static final int IMPOSSIBLE = 1;
	private static final int BEHAVIOR_DELAY = 40;

	private static final int    INITIAL_ATTACK_DAMAGE = 3;
	private static final double INITIAL_ATTACK_DISTANCE = 50.0;

	/* Variables */
	
	// Appearance
	private float size = 8;
	private Color color = Color.BLUE;
	
	private float movement_speed = 4;
	
	private float callDistance;
	public World world;
	private boolean isPlayer;
	private int AILevel = IMPOSSIBLE;
	private SquareDrawer squareDrawer;
	private IHealth health;
	private IAttack attack;
	private final float sightDistance = 300f;
	private int behavior_delay = BEHAVIOR_DELAY;

	/* For AI Hero */
	private IBehavior behavior;

	/* Data Structures */
	public Set<Minion> minions;
	public int numMinions;

	public Hero(float x, float y) {
		super(x, y);

		health = new BasicHealth(MAX_HEALTH);
		attack = new BasicAttack(INITIAL_ATTACK_DAMAGE, INITIAL_ATTACK_DISTANCE);
		minions = new HashSet<Minion>();
		numMinions = 0;

		/* Default call distance: 35 */
		callDistance = 35;

		/* Default behavior: Collect minions */
		behavior = new BehaviorHeroCollectMinions(this);

		// Default squareDrawer : Square squareDrawer 
		squareDrawer = new SquareDrawer();
		squareDrawer.size = 15f;
	}

	public void damage(Entity source, int dmg) {

		// Notify hero's dead 
		/* TODO: Use states stored in the data dictionary 
		if (!squareDrawer.getClass().equals(squareDrawerHeroDamaged.class) && health.damage(dmg)) {
			world.addDeadHero(this);
		} else if (!squareDrawer.getClass().equals(squareDrawerHeroDamaged.class)) {
			setsquareDrawer(new squareDrawerHeroDamaged(this, getsquareDrawer()));
			if (!behavior.getClass().equals(BehaviorHeroAttackTo.class))
				setBehavior(new BehaviorHeroRunFrom(this, source), true);
		}
		*/
	}

	public void draw() {
		squareDrawer.x = x;
		squareDrawer.y = y;
		squareDrawer.draw();
	}

	public void setPlayer(boolean isPlayer) {
		this.isPlayer = isPlayer;
		if(isPlayer)
			this.behavior = new BehaviorHeroPlayer(this);
	}

	public void setWorld(World world) {
		this.world = world;
	}

	/* This will make the hero move */
	public void think(float delta) {

		// Apply movement 
		super.think(delta);
		
		/* Check if enemy minions are too close so we call minions to defend */
		lookForNearbyEnemyMinions();
		
		// TODO: 
		if (!isPlayer) {
			if (RandomData.nextInt() % AILevel == 0)
				behavior.update();
		} else {
			behavior.update();
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


	public void addMinion(Minion m) {
		if (m != null) {
			minions.add(m);
			numMinions++;
		}
	}

	public float getMinionsCallDistance() {
		return callDistance;
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
					float xDistance = m.x - x;
					float yDistance = m.y - y;
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
