package com.tc2r.tc2r.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.tc2r.tc2r.TheGame;


/**
 * Created by nudennie.white on 11/10/17.
 */

public class Player extends B2DSprite {

	private int numCrystals;
	private int totalCrystals;

	public Player(Body body) {

		super(body);

		Texture texture = TheGame.res.getTexture("bunny");
		TextureRegion[] spriteRegions = TextureRegion.split(texture, 32, 32)[0];

		setAnimation(spriteRegions, 1 / 12f);

	}

	public void collectCrystal() {
		numCrystals++;
	}
	public int getNumCrystals() {
		return numCrystals;
	}
	public void setTotalCrystals(int i) {
		totalCrystals = i;
	}
	public int getTotalCyrstals() {
		return totalCrystals;
	}

}
