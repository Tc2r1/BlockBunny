package com.tc2r.tc2r.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tc2r.tc2r.handlers.GameStateManager;
import com.tc2r.tc2r.TheGame;

/**
 * Created by Tc2r on 11/7/2017.
 * <p>
 * Description:
 */
public abstract class GameState {

	protected GameStateManager gsm;
	protected TheGame game;
	protected SpriteBatch spriteBatch;
	protected OrthographicCamera camera;
	protected OrthographicCamera hudCam;

	protected GameState(GameStateManager gsm){
		this.gsm = gsm;
		game = gsm.getGame();
		spriteBatch = game.getSpriteBatch();
		camera = game.getCamera();
		hudCam = game.getHudCam();
	}

	public abstract void handleInput();
	public abstract void update(float delta);
	public abstract void render();
	public abstract void dispose();

}
