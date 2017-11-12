package com.tc2r.tc2r.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tc2r.tc2r.TheGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = TheGame.TITLE;
		config.width = TheGame.V_Width * TheGame.SCALE;
		config.height = TheGame.V_Height * TheGame.SCALE;
		new LwjglApplication(new TheGame(), config);
	}
}
