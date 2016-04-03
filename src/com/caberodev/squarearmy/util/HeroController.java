package com.caberodev.squarearmy.util;

import com.caberodev.squarearmy.components.Component;
import com.caberodev.squarearmy.core.InputEngine;

public class HeroController extends Component {

	private float move_speed = 0f;
	
	public HeroController() {
		super("heroController");
	}

	@Override
	public void think(float delta) {
		
		if (father == null) return;
		
		move_speed = father.data._float("movement_speed");
		
		father.data.setFloat("dx", InputEngine.dir.x * move_speed);
		father.data.setFloat("dy", InputEngine.dir.y * move_speed);
		
//		System.out.println("Speed (" + father.data._float("dx") + ", " + father.data._float("dy") + ")");

//		System.out.println(father.data.get("x") + " " + father.data.get("y"));

		// Save position in Global Pool
		DataDictionary.global.setFloat("playerX", father.data._float("x"));
		DataDictionary.global.setFloat("playerY", father.data._float("y"));

	}
}
