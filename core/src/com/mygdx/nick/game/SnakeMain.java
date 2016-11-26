package com.mygdx.nick.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import menuscreen.MenuScreen;

/**
 * Main class of the game
 * **/
public class SnakeMain extends Game {
	private static final String TAG = SnakeMain.class.getName();

	@Override
	public void create () {
		setScreen(new MenuScreen());
		Gdx.app.log(TAG, "Game Launched!!!");
	}
}
