package com.caberodev.squarearmy.main;

import com.caberodev.squarearmy.appearance.AppearanceMinionDamaged;
import com.caberodev.squarearmy.appearance.AppearanceSquare;
import com.caberodev.squarearmy.appearance.IRenderable;
import com.caberodev.squarearmy.appearance.IRenderator;
import com.caberodev.squarearmy.behavior.BehaviorMinionNoHero;
import com.caberodev.squarearmy.behavior.IBehavior;
import com.caberodev.squarearmy.entity.AbstractMoveableEntity;
import com.caberodev.squarearmy.entity.Color;
import com.caberodev.squarearmy.health.BasicHealth;
import com.caberodev.squarearmy.health.IHealth;

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
public class Minion extends AbstractMoveableEntity implements IRenderable {

	/* Constants */
	private static final int         INITIAL_HP = 10;
	private static final Double ATTACK_DISTANCE = 50.0;
	private static final int      ATTACK_DAMAGE = 1;

	/* Variables */
	private float size = 4;
	private Color color;
	private IHealth health;
	private IBehavior behavior;
	private IRenderator appearance;

	private World world;

	public Minion(World world, float x, float y) {
		super(x, y);
		health = new BasicHealth(INITIAL_HP);

		/* Needed to notify dead */
		this.world = world;

		/* Default behavior: No Hero */
		behavior = new BehaviorMinionNoHero(this);

		/* Default appearance: Square Appearance */
		setAppearance(new AppearanceSquare(this));
	}

	public void damage(int dmg) {

		/* Notify hero minion's dead */
		if (health.damage(dmg)) {
			world.addDeadMinion(this);
		} else if (!appearance.getClass().equals(AppearanceMinionDamaged.class)) {
			setAppearance(new AppearanceMinionDamaged(this, getAppearance()));
		}
	}

	public void render() {
		appearance.render();
	}

	public void update() {
		/* Behavior */
		behavior.update();

		/* Apply movement */
		super.update();

		movementReduction();
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

	public float getSize() {
		return size;
	}

	public IRenderator getAppearance() {
		return appearance;
	}

	public void setAppearance(IRenderator appearance) {
		this.appearance = appearance;
	}

}
