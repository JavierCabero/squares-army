package com.caberodev.squarearmy.components.graphics;

import java.util.ArrayList;

import com.caberodev.squarearmy.components.Component;
import com.caberodev.squarearmy.core.DrawEngine;

/** 
 * 
 *  @author Javier Cabero Guerra <br>
 * 
 */
public abstract class Drawer extends Component {

	private ArrayList<DrawModifier> modifiers = new ArrayList<DrawModifier>();
	
	public Drawer() {
		super("drawer");
		
		DrawEngine.addDrawer(this);
	}

	public void execute() {
		
		// modifiers change the drawing variables
		for(DrawModifier dm : modifiers)
			dm.execute();
		
		// draw with the previous changes
		draw();
	}
	
	public abstract void draw();
	
	@Override
	public void close() {
		super.close();
		DrawEngine.delDrawer(this);
	}
	
	// TODO
//	@Override
//	public void hear(LinkedList<Hearer> sources, DataDictionary message) {
//		String name = message._string("name");
//		
//		if (name.equals("addDrawModifier")) {
//			
//		}
//	}
}
