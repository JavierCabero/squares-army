package com.caberodev.squarearmy.world;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.lwjgl.opengl.Display;

import com.caberodev.squarearmy.Drawer;
import com.caberodev.squarearmy.Thinker;
import com.caberodev.squarearmy.behavior.BehaviorMinionFollowHero;
import com.caberodev.squarearmy.util.Color;
import com.caberodev.squarearmy.util.DataDictionary;
import com.caberodev.squarearmy.util.ListLinker;
import com.caberodev.squarearmy.util.RandomData;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 * Copyright 2015 (c) All Rights Reserved. <br><br>
 *        
 *        
 * World<br>
 * 
 * 	This is one of the most important classes, containing almost ALL the
 * logic in this game.
 * 
 * The information about minions and heroes is stored here.
 */
public class World implements Thinker, Drawer {

	// Constants
	public static final float CUT_DISTANCE   = 768f; /* To cut some calculus */
	private static final Double WORLD_SIZE   = (double) (Display.getWidth() * 2);
	private static final int MAX_NUM_HEROES  = 5;
	private final int MINIONS_MAX_X_DISTANCE = Display.getWidth();
	private final int MINIONS_MAX_Y_DISTANCE = Display.getHeight();
	private final int HEROES_RESPAWN_TIME    = 128;
	
	private int numHeroes = 0;
	private int nextHeroSpawn = HEROES_RESPAWN_TIME + RandomData.nextInt(HEROES_RESPAWN_TIME);
	
	private Color[] colors = new Color[]{Color.BLUE, Color.RED, Color.CYAN, Color.GRAY, Color.GREEN, Color.WHITE, Color.YELLOW};
//	private final Double alpha   = Math.asin(Gdx.graphics.getHeight() / Math.sqrt((Gdx.graphics.getWidth()  * Gdx.graphics.getWidth()) + 
//			                		       									      (Gdx.graphics.getHeight() * Gdx.graphics.getHeight())));

	public World() {

		// //Create an red
		// Hero redEnemy = new Hero((Gdx.graphics.getWidth() * 4) / 5,
		// Gdx.graphics.getHeight() / 2);
		// redEnemy.setWorld(this);
		// redEnemy.setColor(EntityColor.RED);
		//
		// //Create an green
		// Hero greenEnemy = new Hero((Gdx.graphics.getWidth() * 2) / 5,
		// Gdx.graphics.getHeight() / 2);
		// greenEnemy.setWorld(this);
		// greenEnemy.setColor(EntityColor.GREEN);
		//
		// //Create orange
		// Hero cianEnemy = new Hero((Gdx.graphics.getWidth() * 3) / 6,
		// Gdx.graphics.getHeight() / 2);
		// cianEnemy.setWorld(this);
		// cianEnemy.setColor(EntityColor.CIAN);

		// heroes.add(redEnemy);
		// heroes.add(greenEnemy);
		// heroes.add(cianEnemy);
	}

	public void think(float delta) {


//		if (numMinions < MAX_NUM_MINIONS) {
//			int x, y;
//			int i = RandomData.nextInt(MINIONS_MAX_X_DISTANCE);
//			int j = RandomData.nextInt(MINIONS_MAX_Y_DISTANCE);
//			if (i % 2 == 0) {
//				x = (int) player.data._float("x") + Gdx.graphics.getWidth() / 2 + i;
//				if (j % 2 == 0) {
//					y = (int) player.data._float("y") + Gdx.graphics.getHeight() / 2 + j;
//				} else {
//					y = (int) player.data._float("y") - Gdx.graphics.getHeight() / 2 - j;
//				}
//			} else {
//				x = (int) player.data._float("x") - Gdx.graphics.getWidth() / 2 - i;
//				if (j % 2 == 0) {
//					y = (int) player.data._float("y") + Gdx.graphics.getHeight() / 2 + j;
//				} else {
//					y = (int) player.data._float("y") - Gdx.graphics.getHeight() / 2 - j;
//				}
//			}
//			Minion m = new Minion(this, x, y);
//			neutralMinions.add(m);
//			minions.add(m);
//			numMinions++;
//		}

		
		// ToDo: add this to a 'dead world line'
		for (WorldObject minion : ListLinker.get("minion")) {

			float xDistance = minion.data._float("x") - ListLinker.get("player").get(0).data._float("x");
			float yDistance = minion.data._float("y") - ListLinker.get("player").get(0).data._float("y");

			Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

			if (realDistance > WORLD_SIZE) {
				DataDictionary message = new DataDictionary();
				message.set("name", "die");
				minion.hear(null, message);
			}
		}

		for (WorldObject hero : ListLinker.get("heroes")) {
			float xDistance = hero.data._float("x") - DataDictionary.global._float("playerX");
			float yDistance = hero.data._float("y") - DataDictionary.global._float("playerY");

			Double realDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

			if (realDistance > WORLD_SIZE) {
				//addDeadHero(hero);
			}
		}
		//removeDeadMinions();
		//removeDeadHeroes();
	}
/*
	private Set<Color> getColorsUsed() {
		Set<Color> colorsUsed = new HashSet<Color>();

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
*/
	public void draw() {
//		renderArmiesLength();
//		renderHeroesHealth();
//		renderEnemyLocatorArrow();
	}

//	@SuppressWarnings("unused")
//	private void renderEnemyLocatorArrow() {
//
//		for (Hero h : heroes) {
//
//			if (!getScreenRectangle().contains((int) h.data._float("x"), (int) h.data._float("y"))) {
//				/* Calculate arrow place */
//				float x = ((float) Gdx.graphics.getWidth() / 2) - offsetX;
//				float y = ((float) Gdx.graphics.getHeight() / 2) - offsetY;
//				float x1 = 10, y1 = 10, x2 = 100, y2 = 10, x3 = 10, y3 = 100;
//
//				float diffX = h.data._float("x") - x;
//				float diffY = h.data._float("y") - y;
//
//				if (diffX > 0) {
//					if (diffY > 0) {
//						/* Top right */
//						if (Math.asin(diffY / Math.sqrt((diffX * diffX) + (diffY * diffY))) < alpha) {
//							/* key point */
//							y1 = Gdx.graphics.getHeight() / 2 - offsetY + diffY;
//							x1 = Gdx.graphics.getWidth() - 10 - offsetX;
//
//							x2 = x1 - 10;
//							y2 = y1 + 10;
//
//							x3 = x2;
//							y3 = y1 - 10;
//
//						} else {
//							/* key point */
//							y1 = Gdx.graphics.getHeight() - offsetY - 10;
//							x1 = Gdx.graphics.getWidth() / 2 - offsetX + diffX;
//
//							x2 = x1 - 10;
//							y2 = y1 - 10;
//
//							x3 = x1 + 10;
//							y3 = y2;
//						}
//					} else {
//						/* Bottom right */
//
//						if (Math.asin(diffY / Math.sqrt((diffX * diffX) + (diffY * diffY))) > -alpha + (1 / 2)) {
//							/* key point */
//							y1 = Gdx.graphics.getHeight() / 2 - offsetY + diffY;
//							x1 = Gdx.graphics.getWidth() - 10 - offsetX;
//
//							x2 = x1 - 10;
//							y2 = y1 + 10;
//
//							x3 = x2;
//							y3 = y1 - 10;
//
//						} else {
//							/* key point */
//							y1 = -offsetY + 10;
//							x1 = Gdx.graphics.getWidth() / 2 - offsetX + diffX;
//							x2 = x1 - 10;
//							y2 = y1 + 10;
//
//							x3 = x1 + 10;
//							y3 = y2;
//						}
//					}
//				} else {
//					if (diffY > 0) {
//						/* Top left */
//						if (Math.asin(diffY / Math.sqrt((diffX * diffX) + (diffY * diffY))) < alpha + (1 / 2)) {
//							/* key point */
//							y1 = Gdx.graphics.getHeight() / 2 - offsetY + diffY;
//							x1 = 10 - offsetX;
//
//							x2 = x1 + 10;
//							y2 = y1 + 10;
//
//							x3 = x2;
//							y3 = y1 - 10;
//
//						} else {
//							/* key point */
//							y1 = Gdx.graphics.getHeight() - offsetY - 10;
//							x1 = Gdx.graphics.getWidth() / 2 - offsetX + diffX;
//
//							x2 = x1 - 10;
//							y2 = y1 - 10;
//
//							x3 = x1 + 10;
//							y3 = y2;
//						}
//					} else {
//						/* Bottom Left */
//
//						if (Math.asin(diffY / Math.sqrt((diffX * diffX) + (diffY * diffY))) > -alpha + (1 / 2)) {
//							/* key point */
//							y1 = Gdx.graphics.getHeight() / 2 - offsetY + diffY;
//							x1 = 10 - offsetX;
//
//							x2 = x1 + 10;
//							y2 = y1 + 10;
//
//							x3 = x2;
//							y3 = y1 - 10;
//
//						} else {
//							/* key point */
//							y1 = -offsetY + 10;
//							x1 = Gdx.graphics.getWidth() / 2 - offsetX + diffX;
//							x2 = x1 - 10;
//							y2 = y1 + 10;
//
//							x3 = x1 + 10;
//							y3 = y2;
//						}
//					}
//				}
//
//				/* TODO: Render arrow 
//				EntityColor color = h.getColor();
//				glColor3f(color.red, color.green, color.blue);
//
//				glBegin(GL_TRIANGLES);
//				glVertex2f(x1, y1);
//				glVertex2f(x2, y2);
//				glVertex2f(x3, y3);
//				glEnd();
//				*/
//			}
//		}
//
//	}

	float health_bar_scale = 3;
	float army_bar_scale = 1;
	/*
	private void renderHeroesHealth() {
		// All bars will be rendered on top left 
		int numHero = 1;
		
		for (Hero hero : heroes) {
			// Get info 
			Color color = hero.getColor();

			// Render bar
			barRenderer.setColor(color.r, color.g, color.b, color.a);
			barRenderer.begin(ShapeType.Filled);
			barRenderer.rect(15, numHero * 15, hero.getHealth() * health_bar_scale, 10);
			barRenderer.end();
			numHero++;
		}
	}

	private void renderArmiesLength() {
		// All bars will be rendered on top left 
		int numHero = 1;
		for (Hero hero : heroes) {
			// Get info 
			int numMinions = 1 + hero.getNumMinions();
			Color color = hero.getColor();

			// Render bar 
			barRenderer.setColor(color.r, color.g, color.b, color.a);
			barRenderer.begin(ShapeType.Filled);
			barRenderer.rect(Gdx.graphics.getWidth() - 15, numHero * 15, -numMinions * health_bar_scale, 10);
			barRenderer.end();
			numHero++;
		}
	}
	
	private void removeDeadMinions() {
		for (Minion m : deadMinions) {
			removeMinion(m);
			numMinions--;
		}

		deadMinions = new HashSet<Minion>();
	}
	*/
}
