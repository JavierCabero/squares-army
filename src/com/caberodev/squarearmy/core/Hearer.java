package com.caberodev.squarearmy.core;


import java.util.LinkedList;

import com.caberodev.squarearmy.util.DataDictionary;

/**
 * 
 * Author: Javier Cabero Guerra <br>
 * 
 * To hear is to communicate. WorldObjects can hear for incoming messages such as damage, heal, special attacks, etc.
 */
public interface Hearer {

	public void hear(LinkedList<Hearer> sources, DataDictionary message);
}
