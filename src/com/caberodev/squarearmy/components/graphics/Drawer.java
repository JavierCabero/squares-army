package com.caberodev.squarearmy.components.graphics;

import java.util.ArrayList;
import java.util.LinkedList;

import com.caberodev.squarearmy.components.Component;
import com.caberodev.squarearmy.core.DrawEngine;
import com.caberodev.squarearmy.core.Hearer;
import com.caberodev.squarearmy.util.DataDictionary;

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

	public void draw() {
		// TODO
//		for(DrawModifier dm : modifiers)
//			dm.execute();
	};
	
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
