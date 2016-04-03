package com.caberodev.squarearmy.components.behavior;

import java.util.Random;

import com.caberodev.squarearmy.util.DataDictionary;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 */
public class BehaviorMinionNoHero extends Behavior {

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

	public BehaviorMinionNoHero() {
		
		/* This behavior colors a neutral minion (gray) */
		DataDictionary changeColor = new DataDictionary();
		changeColor.setString("name", "drawer");
		changeColor.setString("set:color", "gray");
//		father.hear(null, changeColor); // ToDo: find a way to change color on father set or similar
		
		r = new Random();
		MAX_DELAY = 200 + r.nextInt(MAX_MINION_DELAY);
	}

	@Override
	public void think(float delta) {
		
		if (father == null) return;
		
		/* Waiting a while */
		if (delayUntilNextMove > 0) {
			delayUntilNextMove--;
		} else {
			/* Random move */
			father.data.setFloat("dx", MAX_MOVEMENT / 2f - r.nextInt(MAX_MOVEMENT));
			father.data.setFloat("dy", MAX_MOVEMENT / 2f - r.nextInt(MAX_MOVEMENT));

			/* Random wait */
			delayUntilNextMove = r.nextInt(MAX_DELAY);
		}

	}

}
