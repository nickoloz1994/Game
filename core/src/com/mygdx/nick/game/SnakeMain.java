package com.mygdx.nick.game;

import com.badlogic.gdx.Game;
import menuscreen.MenuScreen;

/**
 * Main class of the game
 * **/
public class SnakeMain extends Game {

	@Override
	public void create () {
		setScreen(new MenuScreen());
	}
}
