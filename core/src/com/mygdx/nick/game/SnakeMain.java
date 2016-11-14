package com.mygdx.nick.game;

import com.badlogic.gdx.Game;
import menuscreen.MenuScreen;

public class SnakeMain extends Game {

	@Override
	public void create () {
		setScreen(new MenuScreen());
	}
}
