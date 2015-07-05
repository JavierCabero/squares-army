package com.caberodev.squarearmy.world;

import com.caberodev.squarearmy.appearance.SquareDrawer;
import com.caberodev.squarearmy.behavior.BehaviorMinionNoHero;
import com.caberodev.squarearmy.behavior.IBehavior;
import com.caberodev.squarearmy.health.BasicHealth;
import com.caberodev.squarearmy.health.IHealth;
import com.caberodev.squarearmy.util.Color;

/** 
 * 
 *  @author Javier Cabero Guerra <br>
 * 
 * Copyright 2015 (c) All Rights Reserved. <br><br>
 * 
 * 
 * Minions <br>
 * 
 * 	They will be found in the World in random places.
 * They can be collected to form an army. Then you will be
 * able to attack and destroy the enemy.
 * 
 * Appearance and Behavior are the essential ingredients.
 */
public class Minion extends WorldObject {

	/* Constants */
	private static final int         INITIAL_HP = 10;
	private static final Double ATTACK_DISTANCE = 50.0;
	private static final int      ATTACK_DAMAGE = 1;

	/* Variables */
	public float size = 4;
	private Color color;
	private IHealth health;
	private IBehavior behavior;
	private SquareDrawer squareDrawer;

	private World world;

	public Minion(World world, float x, float y) {
		super(x, y);
		health = new BasicHealth(INITIAL_HP);

		/* Needed to notify dead */
		this.world = world;

		/* Default behavior: No Hero */
		behavior = new BehaviorMinionNoHero(this);

		// Minions have square appearance
		squareDrawer = new SquareDrawer();
		squareDrawer.size = 5f; // TODO: Load from WorldObject's DataDict
	}

	public void damage(int dmg) {

		/* Notify hero minion's dead */
		if (health.damage(dmg)) {
			world.addDeadMinion(this);
		} /*TODO:else if (!appearance.getClass().equals(AppearanceMinionDamaged.class)) {
			setAppearance(new AppearanceMinionDamaged(this, getAppearance()));
		}
		*/
	}

	public void draw() {
		squareDrawer.x = x;
		squareDrawer.y = y;
		squareDrawer.draw();
	}

	public void think(float delta){
		
		// Apply movement
		super.think(delta);
		
		/* Behavior */
		behavior.update();
	}

	public Object getBehavior() {
		return behavior.getClass();
	}

	public void setBehavior(IBehavior behavior) {
		this.behavior = behavior;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Double getAttackDistance() {
		return ATTACK_DISTANCE;
	}

	public int getAttackDamage() {
		return ATTACK_DAMAGE;
	}
}
