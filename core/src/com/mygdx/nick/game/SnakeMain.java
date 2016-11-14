package com.mygdx.nick.game;

import com.badlogic.gdx.Game;
import gamescreen.GameScreen;

public class SnakeMain extends Game {

	@Override
	public void create () {
		setScreen(new GameScreen());
	}
}
