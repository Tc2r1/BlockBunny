package com.tc2r.tc2r.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tc2r.tc2r.myGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = myGame.TITLE;
		config.width = myGame.V_Width * myGame.SCALE;
		config.height = myGame.V_Height * myGame.SCALE;
		new LwjglApplication(new myGame(), config);
	}
}
