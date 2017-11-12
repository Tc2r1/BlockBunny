package com.tc2r.tc2r.Sprites.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.tc2r.tc2r.Sprites.B2DSprite;
import com.tc2r.tc2r.TheGame;

/**
 * Created by Tc2r on 11/11/2017.
 * <p>
 * Description:
 */
public class Crystal extends B2DSprite {


	public Crystal(Body body) {
		super(body);

		Texture texture = TheGame.res.getTexture("crystal");
		TextureRegion[] sprites = TextureRegion.split(texture, 16, 16)[0];

		setAnimation(sprites, 1 / 12f);
	}
}
