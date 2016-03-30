package com.caberodev.squarearmy.components.stats;

import java.util.LinkedList;

import com.caberodev.squarearmy.components.Component;
import com.caberodev.squarearmy.core.Hearer;
import com.caberodev.squarearmy.util.DataDictionary;

public class Health extends Component {

	public Health() {
		super("health");
		data.set("hp", 10);
	}
	
	@Override
	public void hear(LinkedList<Hearer> sources, DataDictionary message) {
		switch(message._string("name")) {
		case "damage":
			data.set("hp", data._int("hp") - message._int("dp")); // hp: Health Points | dp: Damage Points
			
			// If life is negative or 0: die
			if(data._int("hp") <= 0) { message.set("name", "die"); generateEvent(message); }
		}
	}
}
