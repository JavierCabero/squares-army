package com.caberodev.squarearmy.components.stats;

import java.util.LinkedList;

import com.caberodev.squarearmy.components.Component;
import com.caberodev.squarearmy.core.Hearer;
import com.caberodev.squarearmy.util.DataDictionary;

public class Health extends Component {

	public Health() {
		super("health");
		data.setFloat("hp", 10f);
	}
	
	@Override
	public void hear(LinkedList<Hearer> sources, DataDictionary message) {
		
		String name = message._string("name");
		
		if (name.equals("damage")) {
			
			data.setFloat("hp", data._float("hp") - message._float("damage_points"));
			
			// If life is negative or 0: die
			if(data._float("hp") <= 0) { message.setString("name", "die"); generateEvent(message); }
		} else {
			System.out.println("[Health] Received message \"" + message._string("name") + "\"" + " but no logic was found");
		}
	}
}
