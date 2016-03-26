package com.caberodev.squarearmy.world;

import java.util.LinkedList;

import com.caberodev.squarearmy.Hearer;
import com.caberodev.squarearmy.util.DataDictionary;

public class Health extends Component {

	public Health(WorldObject father) {
		super("health", father);
		data.set("hp", 10);
	}
	
	@Override
	public void hear(LinkedList<Hearer> sources, DataDictionary message) {
		switch(message._string("name")) {
		case "damage":
			data.set("hp", data._int("hp") - message._int("dp")); // hp: Health Points | dp: Damage Points
			
			// If life is negative or 0: die
			if(data._int("hp") <= 0) { message.set("name", "death"); generateEvent(message); }
		}
	}
}
