package com.caberodev.squarearmy.behavior;

import java.util.Random;

import com.caberodev.squarearmy.util.DataDictionary;
import com.caberodev.squarearmy.world.Component;
import com.caberodev.squarearmy.world.WorldObject;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 * Copyright 2015 (c) All Rights Reserved. <br><br>
 *
 */
public class BehaviorMinionNoHero extends Component {

	private Random r;
	private int delayUntilNextMove;

	/*
	 * The specific maximum delay this minion will wait (calculated on
	 * constructor)
	 */
	private final int MAX_DELAY;

	/* Movement max will be between 500 and 1000 */
	private static final int MAX_MINION_DELAY = 200;
	private static final int MAX_MOVEMENT = 10;

	public BehaviorMinionNoHero(WorldObject m) {
		super("behaviorMinionNoHero", m);
		
		/* This behavior colors a neutral minion (gray) */
		DataDictionary changeColor = new DataDictionary();
		changeColor.set("name", "drawer");
		changeColor.set("set:color", "gray");
		father.hear(null, changeColor);
		
		r = new Random();
		MAX_DELAY = 200 + r.nextInt(MAX_MINION_DELAY);
	}

	@Override
	public void think(float delta) {
		/* Waiting a while */
		if (delayUntilNextMove > 0) {
			delayUntilNextMove--;
		} else {
			/* Random move */
			father.data.set("dx", MAX_MOVEMENT / 2 - r.nextInt(MAX_MOVEMENT));
			father.data.set("dy", MAX_MOVEMENT / 2 - r.nextInt(MAX_MOVEMENT));

			/* Random wait */
			delayUntilNextMove = r.nextInt(MAX_DELAY);
		}

	}

}
