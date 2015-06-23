package com.caberodev.squarearmy.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.caberodev.squarearmy.InputEngine;
import com.caberodev.squarearmy.SquareArmy;

public class DesktopLauncher implements InputProcessor {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new SquareArmy(), config);
		Gdx.input.setInputProcessor(new DesktopLauncher());
	}

	private float hero_speed = 0.5f;
	
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		case Input.Keys.A:
			InputEngine.dir.x += -hero_speed;
			return true;
		case Input.Keys.S:
			InputEngine.dir.y += -hero_speed;
			return true;
		case Input.Keys.D:
			InputEngine.dir.x +=  hero_speed;
			return true;
		case Input.Keys.W:
			InputEngine.dir.y +=  hero_speed;
			return true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(keycode) {
		case Input.Keys.A:
			InputEngine.dir.x -= -hero_speed;
			return true;
		case Input.Keys.S:
			InputEngine.dir.y -= -hero_speed;
			return true;
		case Input.Keys.D:
			InputEngine.dir.x -=  hero_speed;
			return true;
		case Input.Keys.W:
			InputEngine.dir.y -=  hero_speed;
			return true;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
