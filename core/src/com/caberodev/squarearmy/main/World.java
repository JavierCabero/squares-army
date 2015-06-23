package com.caberodev.squarearmy.main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.caberodev.squarearmy.DrawEngine;
import com.caberodev.squarearmy.Drawable;
import com.caberodev.squarearmy.LogicEngine;
import com.caberodev.squarearmy.Thinker;
import com.caberodev.squarearmy.behavior.BehaviorMinionFollowHero;
import com.caberodev.squarearmy.entity.EntityColor;
import com.caberodev.squarearmy.util.Rectangle;

/*
 * World
 * 
 * 	This is one of the most important classes, containing almost ALL the
 * logic in this game.
 * 
 * The information about minions and heroes in the world is here.
 */
public class World implements Thinker, Drawable {

	/* Constants */
	public static final float CUT_DISTANCE = 768f; /* To cut some calculus */
	private static final Double WORLD_SIZE = (double) (Boot.SCREEN_WIDTH * 2);
	private static final int MAX_NUM_HEROES = 5;
	private final int MINIONS_MAX_X_DISTANCE = Boot.SCREEN_WIDTH;
	private final int MINIONS_MAX_Y_DISTANCE = Boot.SCREEN_HEIGHT;
	private final int MAX_NUM_MINIONS = 350;
	private final int HEROES_RESPAWN_TIME = 128;

	private Random r = new Random();

	/* Data structures */
	private Set<Minion> minions = new HashSet<Minion>();
	private Set<Minion> neutralMinions = new HashSet<Minion>();
	private List<Hero> heroes = new ArrayList<Hero>();
	private int numMinions = 0;
	private Set<Minion> deadMinions = new HashSet<Minion>();
	private Set<Hero> deadHeroes = new HashSet<Hero>();
	private float camera_speed = 16;
	private Hero player;
	private int numHeroes;
	private EntityColor[] colors = EntityColor.values();
	private final Double alpha = Math.asin(Boot.SCREEN_HEIGHT
			/ Math.sqrt((Boot.SCREEN_WIDTH * Boot.SCREEN_WIDTH) + (Boot.SCREEN_HEIGHT * Boot.SCREEN_HEIGHT)));
	private float offsetX = 0;
	private float offsetY = 0;
	private int heroesRespawnTime = HEROES_RESPAWN_TIME + r.nextInt(HEROES_RESPAWN_TIME);
	// TODO: private Boot boot;
	
	public World(Boot boot, int numNeutralMinions) {

		// this.boot = boot;
		
		/* Create neutral Minions */
		for (int i = 1; i < numNeutralMinions; i++) {
			Minion m = new Minion(this, r.nextInt(MINIONS_MAX_X_DISTANCE), r.nextInt(MINIONS_MAX_Y_DISTANCE));
			minions.add(m);
			/* Redundant list to optimize computation */
			neutralMinions.add(m);
			numMinions++;
		}

		/* Create Hero */
		player = new Hero(Boot.SCREEN_WIDTH / 2, Boot.SCREEN_HEIGHT / 2);
		player.setWorld(this);
		player.setPlayer(true);

		// //Create an red
		// Hero redEnemy = new Hero((Boot.SCREEN_WIDTH * 4) / 5,
		// Boot.SCREEN_HEIGHT / 2);
		// redEnemy.setWorld(this);
		// redEnemy.setColor(EntityColor.RED);
		//
		// //Create an green
		// Hero greenEnemy = new Hero((Boot.SCREEN_WIDTH * 2) / 5,
		// Boot.SCREEN_HEIGHT / 2);
		// greenEnemy.setWorld(this);
		// greenEnemy.setColor(EntityColor.GREEN);
		//
		// //Create orange
		// Hero cianEnemy = new Hero((Boot.SCREEN_WIDTH * 3) / 6,
		// Boot.SCREEN_HEIGHT / 2);
		// cianEnemy.setWorld(this);
		// cianEnemy.setColor(EntityColor.CIAN);

		/* Add both to the list of heroes */
		heroes.add(player);
		// heroes.add(redEnemy);
		// heroes.add(greenEnemy);
		// heroes.add(cianEnemy);
		numHeroes = heroes.size();
		
		DrawEngine.addDrawable(this);
		LogicEngine.addThinker(this);
	}

	public void think(float delta) {

		/* Update heroes */
		for (Hero h : heroes) {
			h.update();
		}
		
		screenAroundPlayer();
		
		/* Update minions */
		for (Minion m : minions) {
			m.update();
		}

		if (numMinions < MAX_NUM_MINIONS) {
			int x, y;
			int i = r.nextInt(MINIONS_MAX_X_DISTANCE);
			int j = r.nextInt(MINIONS_MAX_Y_DISTANCE);
			if (i % 2 == 0) {
				x = (int) player.getX() + Boot.SCREEN_WIDTH / 2 + i;
				if (j % 2 == 0) {
					y = (int) player.getY() + Boot.SCREEN_HEIGHT / 2 + j;
				} else {
					y = (int) player.getY() - Boot.SCREEN_HEIGHT / 2 - j;
				}
			} else {
				x = (int) player.getX() - Boot.SCREEN_WIDTH / 2 - i;
				if (j % 2 == 0) {
					y = (int) player.getY() + Boot.SCREEN_HEIGHT / 2 + j;
				} else {
					y = (int) player.getY() - Boot.SCREEN_HEIGHT / 2 - j;
				}
			}
			Minion m = new Minion(this, x, y);
			neutralMinions.add(m);
			minions.add(m);
			numMinions++;
		}

		for (Minion m : minions) {

			float xDistance = m.getX() - player.getX();
			float yDistance = m.getY() - player.getY();

			Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

			if (realDistance > WORLD_SIZE) {
				addDeadMinion(m);
			}
		}

		if (numHeroes < MAX_NUM_HEROES && heroesRespawnTime <= 0) {
			int x, y;
			int i = r.nextInt(MINIONS_MAX_X_DISTANCE);
			int j = r.nextInt(MINIONS_MAX_Y_DISTANCE);
			if (i % 2 == 0) {
				x = (int) player.getX() + Boot.SCREEN_WIDTH / 2 + i;
				if (j % 2 == 0) {
					y = (int) player.getY() + Boot.SCREEN_HEIGHT / 2 + j;
				} else {
					y = (int) player.getY() - Boot.SCREEN_HEIGHT / 2 - j;
				}
			} else {
				x = (int) player.getX() - Boot.SCREEN_WIDTH / 2 - i;
				if (j % 2 == 0) {
					y = (int) player.getY() + Boot.SCREEN_HEIGHT / 2 + j;
				} else {
					y = (int) player.getY() - Boot.SCREEN_HEIGHT / 2 - j;
				}
			}
			Hero h = new Hero(x, y);
			heroes.add(h);
			h.setWorld(this);

			boolean colorSelected = false;
			int colorIndex = r.nextInt(colors.length);
			EntityColor color = EntityColor.GRAY;
			Set<EntityColor> colorsUsed = getColorsUsed();
			while (!colorSelected) {
				if (!colors[colorIndex].equals(EntityColor.GRAY) && !colors[colorIndex].equals(EntityColor.WHITE)
						&& !colorsUsed.contains(colors[colorIndex])) {
					color = colors[colorIndex];
					colorSelected = true;
				}
				colorIndex = (colorIndex + 1) % colors.length;
			}
			h.setColor(color);

			numHeroes++;
			
			heroesRespawnTime = HEROES_RESPAWN_TIME + r.nextInt(HEROES_RESPAWN_TIME);
		} else {
			heroesRespawnTime--;
		}

		for (Hero h : heroes) {
			float xDistance = h.getX() - player.getX();
			float yDistance = h.getY() - player.getY();

			Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

			if (realDistance > WORLD_SIZE) {
				addDeadHero(h);
			}
		}
		removeDeadMinions();
		removeDeadHeroes();
	}

	private Set<EntityColor> getColorsUsed() {
		Set<EntityColor> colorsUsed = new HashSet<EntityColor>();

		for (Hero h : heroes) {
			colorsUsed.add(h.getColor());
		}
		return colorsUsed;
	}

	private void removeDeadHeroes() {
		for (Hero h : deadHeroes) {
			heroes.remove(h);
			if(h.equals(player)){
				// TODO: boot.setState(2);
			}
			h.freeMinions();
			numHeroes--;
		}

		deadHeroes = new HashSet<Hero>();
	}

	public void draw() {

		/* Render heroes */
		for (Hero h : heroes) {
			h.render();
		}

		/* Render minions */
		for (Minion m : minions) {
			m.render();
		}

		// TODO: renderArmiesLength();
		// TODO: renderHeroesHealth();
		renderEnemyLocatorArrow();
	}

	@SuppressWarnings("unused")
	private void renderEnemyLocatorArrow() {

		for (Hero h : heroes) {

			if (!getScreenRectangle().contains((int) h.getX(), (int) h.getY())) {
				/* Calculate arrow place */
				float x = ((float) Boot.SCREEN_WIDTH / 2) - offsetX;
				float y = ((float) Boot.SCREEN_HEIGHT / 2) - offsetY;
				float x1 = 10, y1 = 10, x2 = 100, y2 = 10, x3 = 10, y3 = 100;

				float diffX = h.getX() - x;
				float diffY = h.getY() - y;

				if (diffX > 0) {
					if (diffY > 0) {
						/* Top right */
						if (Math.asin(diffY / Math.sqrt((diffX * diffX) + (diffY * diffY))) < alpha) {
							/* key point */
							y1 = Boot.SCREEN_HEIGHT / 2 - offsetY + diffY;
							x1 = Boot.SCREEN_WIDTH - 10 - offsetX;

							x2 = x1 - 10;
							y2 = y1 + 10;

							x3 = x2;
							y3 = y1 - 10;

						} else {
							/* key point */
							y1 = Boot.SCREEN_HEIGHT - offsetY - 10;
							x1 = Boot.SCREEN_WIDTH / 2 - offsetX + diffX;

							x2 = x1 - 10;
							y2 = y1 - 10;

							x3 = x1 + 10;
							y3 = y2;
						}
					} else {
						/* Bottom right */

						if (Math.asin(diffY / Math.sqrt((diffX * diffX) + (diffY * diffY))) > -alpha + (1 / 2)) {
							/* key point */
							y1 = Boot.SCREEN_HEIGHT / 2 - offsetY + diffY;
							x1 = Boot.SCREEN_WIDTH - 10 - offsetX;

							x2 = x1 - 10;
							y2 = y1 + 10;

							x3 = x2;
							y3 = y1 - 10;

						} else {
							/* key point */
							y1 = -offsetY + 10;
							x1 = Boot.SCREEN_WIDTH / 2 - offsetX + diffX;
							x2 = x1 - 10;
							y2 = y1 + 10;

							x3 = x1 + 10;
							y3 = y2;
						}
					}
				} else {
					if (diffY > 0) {
						/* Top left */
						if (Math.asin(diffY / Math.sqrt((diffX * diffX) + (diffY * diffY))) < alpha + (1 / 2)) {
							/* key point */
							y1 = Boot.SCREEN_HEIGHT / 2 - offsetY + diffY;
							x1 = 10 - offsetX;

							x2 = x1 + 10;
							y2 = y1 + 10;

							x3 = x2;
							y3 = y1 - 10;

						} else {
							/* key point */
							y1 = Boot.SCREEN_HEIGHT - offsetY - 10;
							x1 = Boot.SCREEN_WIDTH / 2 - offsetX + diffX;

							x2 = x1 - 10;
							y2 = y1 - 10;

							x3 = x1 + 10;
							y3 = y2;
						}
					} else {
						/* Bottom Left */

						if (Math.asin(diffY / Math.sqrt((diffX * diffX) + (diffY * diffY))) > -alpha + (1 / 2)) {
							/* key point */
							y1 = Boot.SCREEN_HEIGHT / 2 - offsetY + diffY;
							x1 = 10 - offsetX;

							x2 = x1 + 10;
							y2 = y1 + 10;

							x3 = x2;
							y3 = y1 - 10;

						} else {
							/* key point */
							y1 = -offsetY + 10;
							x1 = Boot.SCREEN_WIDTH / 2 - offsetX + diffX;
							x2 = x1 - 10;
							y2 = y1 + 10;

							x3 = x1 + 10;
							y3 = y2;
						}
					}
				}

				/* TODO: Render arrow 
				EntityColor color = h.getColor();
				glColor3f(color.red, color.green, color.blue);

				glBegin(GL_TRIANGLES);
				glVertex2f(x1, y1);
				glVertex2f(x2, y2);
				glVertex2f(x3, y3);
				glEnd();
				*/
			}
		}

	}

	/*
	private void renderHeroesHealth() {
		// All bars will be rendered on top left 
		int numHero = 1;
		for (Hero hero : heroes) {
			// Get info 
			int heroHealth = hero.getHealth() * 3;
			EntityColor color = hero.getColor();

			// Render bar 
			glColor3f(color.red, color.green, color.blue);

			glBegin(GL_QUADS);
			glVertex2f(-offsetX + Boot.SCREEN_WIDTH - 15, -offsetY + Boot.SCREEN_HEIGHT - numHero * 15); // Upper-left
			glVertex2f(-offsetX + Boot.SCREEN_WIDTH - 15 - heroHealth, -offsetY + Boot.SCREEN_HEIGHT - numHero * 15); // Upper-right
			glVertex2f(-offsetX + Boot.SCREEN_WIDTH - 15 - heroHealth, -offsetY + Boot.SCREEN_HEIGHT - numHero * 15
					- 10); // Bottom-right
			glVertex2f(-offsetX + Boot.SCREEN_WIDTH - 15, -offsetY + Boot.SCREEN_HEIGHT - numHero * 15 - 10); // Botton-left
			glEnd();
			numHero++;
		}
	}

	private void renderArmiesLength() {
		// All bars will be rendered on top left 
		int numHero = 1;
		for (Hero hero : heroes) {
			// Get info 
			int numMinions = 1 + hero.getNumMinions();
			EntityColor color = hero.getColor();

			// Render bar 
			glColor3f(color.red, color.green, color.blue);

			glBegin(GL_QUADS);
			glVertex2f(-offsetX + 15, -offsetY + Boot.SCREEN_HEIGHT - numHero * 15); // Upper-left
			glVertex2f(-offsetX + 15 + numMinions, -offsetY + Boot.SCREEN_HEIGHT - numHero * 15); // Upper-right
			glVertex2f(-offsetX + 15 + numMinions, -offsetY + Boot.SCREEN_HEIGHT - numHero * 15 - 10); // Bottom-right
			glVertex2f(-offsetX + 15, -offsetY + Boot.SCREEN_HEIGHT - numHero * 15 - 10); // Botton-left
			glEnd();
			numHero++;
		}
	}
	*/
	
	private void removeDeadMinions() {
		for (Minion m : deadMinions) {
			removeMinion(m);
			numMinions--;
		}

		deadMinions = new HashSet<Minion>();
	}

	public void setHeroes(List<Hero> heroes) {
		this.heroes = heroes;
	}

	public List<Hero> getHeroes() {
		return heroes;
	}

	public void addDeadMinion(Minion m) {
		deadMinions.add(m);
	}

	public void setMinions(Set<Minion> minions) {
		this.minions = minions;
	}

	public List<Hero> getHeroes(List<Hero> heroes) {
		return heroes;
	}

	public Set<Minion> getMinions(Set<Minion> minions) {
		return minions;
	}

	/* Heroes will call for minions for their army */
	public void requestMinionsFor(Hero hero) {

		Set<Minion> minionsCaptured = new HashSet<Minion>();

		for (Minion m : neutralMinions) {

			float xDistance = m.getX() - hero.getX();
			float yDistance = m.getY() - hero.getY();

			Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

			/* If close enough */
			if (realDistance <= hero.getMinionsCallDistance()) {

				/* Minion captured */
				m.setBehavior(new BehaviorMinionFollowHero(hero, m));
				minionsCaptured.add(m);
				hero.addMinion(m);
				
			}

		}

		neutralMinions.removeAll(minionsCaptured);
	}

	public void freeMinion(Minion m) {
		neutralMinions.add(m);
	}

	public void screenAroundPlayer() {
		/* x axis */
		float x = heroes.get(0).getX() + offsetX;
		float dx = Boot.SCREEN_WIDTH / 2 - x;

		dx /= camera_speed;

		// TODO: glTranslatef(dx, 0, 0);
		offsetX += dx;

		/* y axis */
		float y = heroes.get(0).getY() + offsetY;
		float dy = Boot.SCREEN_HEIGHT / 2 - y;

		dy /= camera_speed;
		// TODO: glTranslatef(0, dy, 0);
		offsetY += dy;

	}

	public Set<Minion> getMinions() {
		return minions;
	}

	public void removeMinion(Minion m) {
		/* We don't know where the minion is so we remove it from everywhere */

		minions.remove(m);

		for (Hero h : heroes) {
			h.removeMinion(m);
		}
	}

	public Set<Minion> getDeadMinions() {
		return deadMinions;
	}

	public void setDeadMinions(Set<Minion> deadMinions) {
		this.deadMinions = deadMinions;
	}

	public void addDeadHero(Hero h) {
		deadHeroes.add(h);
	}

	public Rectangle getScreenRectangle() {
		return new Rectangle((int) -offsetX, (int) -offsetY, Boot.SCREEN_WIDTH, Boot.SCREEN_HEIGHT);
	}

	public Set<Minion> getNeutralMinions() {
		return neutralMinions;
	}
}
