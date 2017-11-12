package com.tc2r.tc2r.Sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.tc2r.tc2r.handlers.Animation;
import com.tc2r.tc2r.handlers.B2DVars;

/**
 * Created by nudennie.white on 11/10/17.
 */

public class B2DSprite {

	protected Body body;
	protected Animation animation;
	protected float width, height;

	public B2DSprite(Body body) {
		this.body = body;
		animation = new Animation();
	}

	public void setAnimation(TextureRegion[]region, float delay){
		animation.setFrames(region, delay);
		width = region[0].getRegionWidth();
		height = region[0].getRegionHeight();
	}

	public void update(float delta){
		animation.update(delta);

	}
	public void render(SpriteBatch spriteBatch){
		spriteBatch.draw(
						animation.getFrame(),
						body.getPosition().x * B2DVars.PPM - width / 2,
						body.getPosition().y * B2DVars.PPM - height / 2
		);

	}

	public Body getBody(){
		return body;
	}
	public Vector2 getPosition(){
		return body.getPosition();
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

}
