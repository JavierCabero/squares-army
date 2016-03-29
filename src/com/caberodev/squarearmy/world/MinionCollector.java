package com.caberodev.squarearmy.world;

import java.util.ArrayList;
import java.util.LinkedList;

import com.caberodev.squarearmy.components.Component;
import com.caberodev.squarearmy.core.Hearer;
import com.caberodev.squarearmy.core.WorldObject;
import com.caberodev.squarearmy.util.DataDictionary;
import com.caberodev.squarearmy.util.ListLinker;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 * Picks up nearby minion to follow the hero.
 *
 */
public class MinionCollector extends Component {

	private ArrayList<WorldObject> minions;
	
	public MinionCollector() {
		super("minionCollector");
		minions = new ArrayList<WorldObject>();
	}
	
	@Override
	public void think(float delta) {
		for (WorldObject wo : ListLinker.get("WorldObject")) {
			if (wo.data._string("class").equals("minion") && wo.data._string("hero") == null) {
				DataDictionary message = new DataDictionary();
				message.set("changeBehavior", "behaviorFollowHero");
				message.set("hero", father);
				wo.hear(null, message);
			}
		}
	}
	
	@Override
	public void hear(LinkedList<Hearer> sources, DataDictionary message) {
		
	}
}
