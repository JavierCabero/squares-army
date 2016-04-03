package com.caberodev.squarearmy.core;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 * The way World Objects update themselves is by thinking. All WorldObjects are thinkers.
 * 
 */
public interface Thinker {

	public void think(float delta);
}
