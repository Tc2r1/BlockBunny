package com.tc2r.tc2r.handlers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

/**
 * Created by nudennie.white on 11/7/17.
 */

public class MyInputProcessor extends InputAdapter {

	public boolean mouseMoved(int x, int y){
		MyInput.x = x;
		MyInput.y = y;
		return true;
	}

	public boolean touchDragged(int x, int y, int pointer) {
		MyInput.x = x;
		MyInput.y = y;
		MyInput.down = true;
		return true;
	}

	public boolean touchDown(int x, int y, int pointer, int button) {
		MyInput.x = x;
		MyInput.y = y;
		MyInput.down = true;
		return true;
	}

	public boolean touchUp(int x, int y, int pointer, int button) {
		MyInput.x = x;
		MyInput.y = y;
		MyInput.down = false;
		return true;
	}

	@Override
	public boolean keyDown(int k) {
		if(k == Input.Keys.Z){
			MyInput.setKey(MyInput.BUTTON1, true);

		}
		if(k == Input.Keys.X){
			MyInput.setKey(MyInput.BUTTON2, true);
		}
		return true;
	}

	@Override
	public boolean keyUp(int k) {
		if(k == Input.Keys.Z){
			MyInput.setKey(MyInput.BUTTON1, false);

		}
		if(k == Input.Keys.X){
			MyInput.setKey(MyInput.BUTTON2, false);
		}
		return true;
	}
}
