package com.caberodev.squarearmy.appearance;

import com.caberodev.squarearmy.DrawEngine;
import com.caberodev.squarearmy.Drawer;
import com.caberodev.squarearmy.world.Component;
import com.caberodev.squarearmy.world.WorldObject;

/** 
 * 
 *  @author Javier Cabero Guerra <br>
 * 
 * Copyright 2015 (c) All Rights Reserved. <br><br>
 * 
 */
public abstract class ShapeDrawer extends Component implements Drawer {

	public ShapeDrawer(String name, WorldObject father) {
		super(name, father);
		
		DrawEngine.addDrawer(this);
	}

	@Override
	public void close() {
		super.close();
		DrawEngine.delDrawer(this);
	}
}
