package behavior;

import java.util.Random;

import main.Minion;

import entity.EntityColor;

/* Random moves at random intervals */

public class BehaviorMinionNoHero implements IBehavior {

	private Random r;
	private int delayUntilNextMove;
	private Minion m;

	/*
	 * The specific maximum delay this minion will wait (calculated on
	 * constructor)
	 */
	private final int MAX_DELAY;

	/* Movement max will be between 500 and 1000 */
	private static final int MAX_MINION_DELAY = 200;
	private static final int MAX_MOVEMENT = 10;

	public BehaviorMinionNoHero(Minion m) {
		this.m = m;
		/* This behavior colors a neutral minion (gray) */
		m.setColor(EntityColor.GRAY);

		r = new Random();
		MAX_DELAY = 200 + r.nextInt(MAX_MINION_DELAY);
	}

	public void update() {
		/* Waiting a while */
		if (delayUntilNextMove > 0) {
			delayUntilNextMove--;
		} else {
			/* Random move */
			m.setDx(MAX_MOVEMENT / 2 - r.nextInt(MAX_MOVEMENT));
			m.setDy(MAX_MOVEMENT / 2 - r.nextInt(MAX_MOVEMENT));

			/* Random wait */
			delayUntilNextMove = r.nextInt(MAX_DELAY);
		}

	}

}