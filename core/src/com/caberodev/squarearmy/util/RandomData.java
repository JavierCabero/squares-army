package com.caberodev.squarearmy.util;

import java.util.Random;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 * Copyright 2015 (c) All Rights Reserved.
 *                                          
 */
public class RandomData {

	private static Random r = new Random();
	
	public static boolean nextBoolean() {
		return r.nextBoolean();
	}

	public static int nextInt() {
		return r.nextInt();
	}
	
	public static int nextInt(int max) {
		return r.nextInt(max);
	}
	public static double nextFloat() {
		return r.nextFloat();
	}
	
	public static double nextDouble() {
		return r.nextDouble();
	}
}
