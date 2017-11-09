package com.tc2r.tc2r;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tc2r.tc2r.handlers.Content;
import com.tc2r.tc2r.handlers.GameStateManager;
import com.tc2r.tc2r.handlers.MyInput;
import com.tc2r.tc2r.handlers.MyInputProcessor;

public class myGame extends ApplicationAdapter {

	public static final String TITLE = "Block Bunny";
	public static final int V_Width = 320;
	public static final int V_Height = 240;
	public static final int SCALE = 2;

	public static final float STEP = 1/60f;
	private float accum;

	private SpriteBatch spriteBatch;
	private OrthographicCamera camera;
	private OrthographicCamera hudCam;

	private GameStateManager gsm;

	public static Content res;

	@Override
	public void create() {

		Gdx.input.setInputProcessor(new MyInputProcessor());

		res = new Content();
		res.loadTexture("images/bunny.png", "bunny");

		spriteBatch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, V_Width, V_Height);
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, V_Width, V_Height);
		gsm = new GameStateManager(this);

	}



	@Override
	public void render() {


		accum += Gdx.graphics.getDeltaTime();
		while (accum >= STEP) {
			accum -= STEP;
			gsm.update(STEP);
			gsm.render();
			MyInput.update();
		}


	}

	@Override
	public void dispose() {

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public OrthographicCamera getHudCam() {
		return hudCam;
	}
}
