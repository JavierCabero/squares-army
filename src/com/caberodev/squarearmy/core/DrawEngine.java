package com.caberodev.squarearmy.core;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.caberodev.squarearmy.HeroCamera;
import com.caberodev.squarearmy.Vec4;
import com.caberodev.squarearmy.appearance.Drawer;

/**
 * 
 * @author Javier Cabero Guerra <br>
 * 
 * Copyright 2015 (c) All Rights Reserved.
 *                                          
 */
public class DrawEngine {
	
	/* Constant: Where essential data is */
	public final static int SCREEN_WIDTH = 800;
	public final static int SCREEN_HEIGHT = 600;
	
	// Camera handler
	public static HeroCamera camera;
	
	// Default font for 2D text
	//public static BitmapFont font;

	// List for drawables
	private static ArrayList<Drawer> drawList;
	
	// Background color
	public static Vec4 bgColor = new Vec4(0.58f, 0.58f, 0.7f, 1);
	
	public static void addDrawer(Drawer item) {
		drawList.add(item);
	}
	
	public static void delDrawer(Drawer item) {
		drawList.remove(item);
	}
	
	public static void start() {

		drawList = new ArrayList<Drawer>();
       
        setUpScreen();
        
        camera   = new HeroCamera();
    }
	
	private static void setUpScreen() {
		try {
			Display.setDisplayMode(new DisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT));
			Display.setTitle("Square armys");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		// Initialization code OpenGL
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, SCREEN_WIDTH, 0, SCREEN_HEIGHT, 1, -1);

		glMatrixMode(GL_MODELVIEW);

	}
	
	public static void draw() {
		
		// Update game camera
		camera.followHeroPlayer(); 
		/*
		ShapeDrawer.shapeRenderer.setProjectionMatrix(camera.combined);
		
		// Clear and set background tint
		Gdx.gl.glClearColor(bgColor.x, bgColor.y, bgColor.z, bgColor.w);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Shape drawing
		batch.begin();
		*/
		for(Drawer item : drawList) 
			item.draw();
		/*
		batch.end();
		
		// Text drawing
		batch.begin();
		for(Text2D item : textList)
			item.draw();
		batch.end();
		*/
	}
}
