package com.caberodev.squarearmy.appearance;

import com.caberodev.squarearmy.components.Component;
import com.caberodev.squarearmy.core.DrawEngine;

/** 
 * 
 *  @author Javier Cabero Guerra <br>
 * 
 */
public abstract class Drawer extends Component {

	public Drawer() {
		super("drawer");
		
		DrawEngine.addDrawer(this);
	}

	public abstract void draw();
	
	@Override
	public void close() {
		super.close();
		DrawEngine.delDrawer(this);
	}
}
