package com.tc2r.tc2r.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.tc2r.tc2r.handlers.B2DVars;
import com.tc2r.tc2r.handlers.GameStateManager;
import com.tc2r.tc2r.myGame;

import static com.tc2r.tc2r.handlers.B2DVars.PPM;

/**
 * Created by Tc2r on 11/7/2017.
 * <p>
 * Description:
 */
public class Play extends GameState {

	private World world;
	private Box2DDebugRenderer b2dr;

	public OrthographicCamera b2dCam;


	public Play(GameStateManager gameStateManager) {
		super(gameStateManager);

		world = new World(new Vector2(0, -9.81f), true);
		b2dr = new Box2DDebugRenderer();

		// Create platform
		BodyDef bDef = new BodyDef();
		bDef.position.set(160 / PPM, 120 / PPM);
		bDef.type = BodyDef.BodyType.StaticBody;

		Body body = world.createBody(bDef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(50/ PPM, 5/ PPM);

		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_GROUND;
		fdef.filter.maskBits = B2DVars.BIT_BOX | B2DVars.BIT_BALL;
		body.createFixture(fdef);

		// create falling box

		bDef.position.set(160 /PPM, 160 /PPM);
		bDef.type = BodyDef.BodyType.DynamicBody;

		body = world.createBody(bDef);
		shape = new PolygonShape();
		shape.setAsBox(5/ PPM, 5/ PPM);

		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_BOX;
		fdef.filter.maskBits =
						B2DVars.BIT_GROUND |
										B2DVars.BIT_BOX;
		fdef.restitution = 1f;
		body.createFixture(fdef);

		//create ball
		bDef.position.set(160 / PPM, 220 / PPM);
		body = world.createBody(bDef);

		CircleShape cShape = new CircleShape();
		cShape.setRadius(5/ PPM);
		fdef.shape= cShape;
		fdef.restitution = 1f;
		fdef.filter.categoryBits = B2DVars.BIT_BALL;
		fdef.filter.maskBits =
						B2DVars.BIT_GROUND |
										B2DVars.BIT_BALL;


		body.createFixture(fdef);

		// set up box2d Cam
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, myGame.V_Width / PPM, myGame.V_Height/ PPM);
	}

	@Override
	public void handleInput() {

	}

	@Override
	public void update(float delta) {

		world.step(delta, 6, 2);
	}

	@Override
	public void render() {

		// clear screen
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		// draw world
		b2dr.render(world, b2dCam.combined);

	}

	@Override
	public void dispose() {

	}
}
