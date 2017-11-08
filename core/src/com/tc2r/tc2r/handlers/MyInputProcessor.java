package com.tc2r.tc2r.handlers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

/**
 * Created by nudennie.white on 11/7/17.
 */

public class MyInputProcessor extends InputAdapter {



	@Override
	public boolean keyDown(int k) {
		if(k == Input.Keys.Z){
			MyInput.setKeys(MyInput.BUTTON1, true);

		}
		if(k == Input.Keys.X){
			MyInput.setKeys(MyInput.BUTTON2, true);
		}
		return true;
	}

	@Override
	public boolean keyUp(int k) {
		if(k == Input.Keys.Z){
			MyInput.setKeys(MyInput.BUTTON1, false);

		}
		if(k == Input.Keys.X){
			MyInput.setKeys(MyInput.BUTTON2, false);
		}
		return true;
	}
}
