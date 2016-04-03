package com.caberodev.squarearmy.components.misc;

import java.util.LinkedList;

import com.caberodev.squarearmy.components.Component;
import com.caberodev.squarearmy.core.Hearer;
import com.caberodev.squarearmy.util.DataDictionary;
import com.caberodev.squarearmy.util.ListLinker;
import com.caberodev.squarearmy.worldobjects.WorldObject;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 * Picks up nearby minion to follow the hero.
 *
 */
public class MinionCollector extends Component {

//	private ArrayList<WorldObject> minions = new ArrayList<WorldObject>();
	
	public MinionCollector() {
		super("minionCollector");
	}
	
	@Override
	public void think(float delta) {
		for (WorldObject wo : ListLinker.get("WorldObject")) {
			if (wo.data._string("class").equals("minion") && wo.data._string("hero") == null) {
				DataDictionary message = new DataDictionary();
				message.setString("changeBehavior", "behaviorFollowHero");
				message.setWorldObject("hero", father);
				wo.hear(null, message);
			}
		}
	}
	
	@Override
	public void hear(LinkedList<Hearer> sources, DataDictionary message) {
		// TODO
	}
}
