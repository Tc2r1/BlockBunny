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

	private HashMap<String, Texture> textures;

	public Content(){
		textures = new HashMap<String, Texture>();
	}

	public void loadTexture(String path, String key){
		Texture tex = new Texture(Gdx.files.internal(path));
		textures.put(key, tex);
	}

	public Texture getTexture(String key) {
		return textures.get(key);

	}

	public void disposeTexture(String key) {
		Texture tex = textures.remove(key);
		if(tex != null){
			tex.dispose();
		}

	}
	}