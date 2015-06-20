package com.caberodev.squarearmy.health;
public class BasicHealth implements IHealth {

	private int hp;

	public BasicHealth(int hp) {
		this.hp = hp;
	}

	public boolean damage(int dmg) {
		boolean dead = false;

		hp -= dmg;

		if (hp < 0)
			dead = true;

		return dead;
	}
	
	public int getHealth(){
		return hp;
	}
}
