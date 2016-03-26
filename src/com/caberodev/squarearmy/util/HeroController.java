package com.caberodev.squarearmy.util;

import com.caberodev.squarearmy.InputEngine;
import com.caberodev.squarearmy.world.Component;
import com.caberodev.squarearmy.world.WorldObject;

public class HeroController extends Component {

	private float move_speed = 15f;
	
	public HeroController(WorldObject father) {
		super("heroController", father);
	}

	@Override
	public void think(float delta) {

		father.data.set("dx", InputEngine.dir.x * move_speed);
		father.data.set("dy", InputEngine.dir.y * move_speed);

//		System.out.println(father.data.get("x") + " " + father.data.get("y"));

		// Save position in Global Pool
		DataDictionary.global.set("playerX", father.data._float("x"));
		DataDictionary.global.set("playerY", father.data._float("y"));

	}
}
