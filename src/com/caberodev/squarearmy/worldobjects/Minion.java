package com.caberodev.squarearmy.worldobjects;

import com.caberodev.squarearmy.components.behavior.BehaviorMinionNoHero;
import com.caberodev.squarearmy.components.graphics.SquareDrawer;
import com.caberodev.squarearmy.components.stats.Health;
import com.caberodev.squarearmy.components.weapons.WeaponStandard;
import com.caberodev.squarearmy.util.DataDictionary;

public class Minion extends WorldObject {

	private static int count = 0;
	
	public Minion() {}
	
	public Minion(DataDictionary data) {
		
		super(data);
		
		data.setFloat("size", 5f);
		
		addComponent(new Health());
		addComponent(new WeaponStandard());
		addComponent(new BehaviorMinionNoHero());
		addComponent(new SquareDrawer());
		
		System.err.println("Minion created (" + count++ + ")");
	}
}
