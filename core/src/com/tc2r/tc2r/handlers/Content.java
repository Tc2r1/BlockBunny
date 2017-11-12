package com.tc2r.tc2r.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

/**
 * Created by Tc2r on 11/9/2017.
 * <p>
 * Description:
 */
public class Content {

	private HashMap<String, Texture> textureHashMap;

	public Content() {
		textureHashMap = new HashMap<String, Texture>();
	}

	public void loadTexture(String path, String key) {
		Texture texture = new Texture(Gdx.files.internal(path));
		textureHashMap.put(key, texture);
	}

	public Texture getTexture(String key) {
		return textureHashMap.get(key);
	}

	public void disposeTexture(String key) {
		Texture texture = textureHashMap.get(key);
		if(texture != null) texture.dispose();
	}

}
