package com.tc2r.tc2r.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tc2r.tc2r.Sprites.Player;
import com.tc2r.tc2r.handlers.B2DVars;
import com.tc2r.tc2r.TheGame;

/**
 * Created by Tc2r on 11/11/2017.
 * <p>
 * Description:
 */
public class HUD {
	private Player player;
	private TextureRegion[] blockRegions;

	public HUD(Player player){
		this.player = player;

		Texture texture = TheGame.res.getTexture("hud");

		blockRegions = new TextureRegion[3];
		for (int i = 0; i < blockRegions.length; i++) {
			blockRegions[i] = new TextureRegion(texture, 32 + i * 16, 0, 16, 16);

		}
	}

	public void render(SpriteBatch spriteBatch) {
		short bits = player.getBody().getFixtureList()
						.first().getFilterData().maskBits;


		// Draw Block TYPE IN HUD

		if((bits & B2DVars.BIT_RED) != 0){
			spriteBatch.draw(blockRegions[0], 40, 200);
		}
		if((bits & B2DVars.BIT_GREEN) != 0){
			spriteBatch.draw(blockRegions[1], 40, 200);
		}
		if((bits & B2DVars.BIT_BLUE) != 0){
			spriteBatch.draw(blockRegions[2], 40, 200);
		}
	}
}
