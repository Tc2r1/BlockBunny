package com.tc2r.tc2r.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.tc2r.tc2r.TheGame;
import com.tc2r.tc2r.Sprites.Items.Crystal;
import com.tc2r.tc2r.entities.HUD;
import com.tc2r.tc2r.Sprites.Player;
import com.tc2r.tc2r.handlers.B2DVars;
import com.tc2r.tc2r.handlers.GameStateManager;
import com.tc2r.tc2r.handlers.MyContactListener;
import com.tc2r.tc2r.handlers.MyInput;

import static com.tc2r.tc2r.handlers.B2DVars.PPM;

/**
 * Created by Tc2r on 11/7/2017.
 * <p>
 * Description:
 */
public class Play extends GameState {

	private boolean debug = true;

	private static final float GRAVITY = -9.81f;
	private static final float PLAYER_JUMP = 150f;
	private static final float PLAYER_SPEED = 1f;
	private static final float PlAYER_TEXTURE_SIZE = 26f;
	private static final String TILE_MAP_LEVEL = "maps/test.tmx";
	private static final float START_X = 50f;
	private static final float START_Y = 200f;

	private World world;
	private Box2DDebugRenderer b2dr;
	public OrthographicCamera b2dCam;
	private HUD hud;

	private MyContactListener cl;
	private Player player;
	private Array<Crystal> crystalArray;

	private TiledMap tiledMap;
	private float tileSize;
	private OrthogonalTiledMapRenderer tmr;

	public Play(GameStateManager gameStateManager) {
		super(gameStateManager);

		//Setup Box2D
		world = new World(new Vector2(0, GRAVITY), true);
		cl = new MyContactListener();
		world.setContactListener(cl);
		b2dr = new Box2DDebugRenderer();

		// Create Player
		createPlayer();

		// Create Tiles
		createTiles();

		// Create Crystal
		createCrystals();

		// Hud
		hud = new HUD(player);

		// set up box2d Cam
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, TheGame.V_Width / PPM, TheGame.V_Height / PPM);
	}

	@Override
	public void handleInput() {

		// KEYBOARD INPUT

			// Player Jump Control
		if (MyInput.isPressed(MyInput.BUTTON1) && cl.isPlayerOnGround()) {
			Gdx.app.log("CONTROL", "JUMPED \n");
			player.getBody().applyForceToCenter(0, PLAYER_JUMP, true);
		}

			// Switch Block color
		if (MyInput.isPressed(MyInput.BUTTON2)) {
			switchBlocks();
		}

		// MOUSE / TOUCH INPUT

		// Left side of screen switches blocks, right side of the screen jumps!

		if (MyInput.isPressed(MyInput.BUTTON1) && cl.isPlayerOnGround()) {
			player.getBody().applyForceToCenter(0, PLAYER_JUMP, true);
		}

		// Switch Block color
		if (MyInput.isPressed()) {
			if(MyInput.x < Gdx.graphics.getWidth() /2 ) {
				switchBlocks();
			} else {
				player.getBody().applyForceToCenter(0, PLAYER_JUMP, true);
			}
		}

	}

	private void switchBlocks() {
		Filter filter = player.getBody().getFixtureList().first().getFilterData();

		short bits = filter.maskBits;

		// switch to next color

		// red -> green -> blue -> red


		if ((bits & B2DVars.BIT_RED) != 0) {
			bits &= ~B2DVars.BIT_RED;
			bits |= B2DVars.BIT_GREEN;
		} else if ((bits & B2DVars.BIT_GREEN) != 0) {
			bits &= ~B2DVars.BIT_GREEN;
			bits |= B2DVars.BIT_BLUE;
		} else if ((bits & B2DVars.BIT_BLUE) != 0) {
			bits &= ~B2DVars.BIT_BLUE;
			bits |= B2DVars.BIT_RED;
		}

		// set new mask bits
		filter.maskBits = bits;
		player.getBody().getFixtureList().first().setFilterData(filter);

		// set new mask bits for foot
		filter = player.getBody().getFixtureList().get(1).getFilterData();
		bits &= ~B2DVars.BIT_CRYSTAL;
		filter.maskBits = bits;
		player.getBody().getFixtureList().get(1).setFilterData(filter);


	}

	@Override
	public void update(float delta) {

		// Check Input
		handleInput();

		// Update box2d
		world.step(delta, 6, 2);

		/// remove crystalArray after world finishes update
		Array<Body> bodies = cl.getBodiesToRemove();
		for (Body b : bodies) {
			crystalArray.removeValue((Crystal) b.getUserData(), true);
			world.destroyBody(b);
			player.collectCrystal();
		}
		bodies.clear();

		player.update(delta);

		for (Crystal c : crystalArray) {

			c.update(delta);
		}
	}

	@Override
	public void render() {

		// clear screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// set camera to follow player
		camera.position.set(player.getPosition().x * PPM + TheGame.V_Width / 4, TheGame.V_Height / 2, 0);
		camera.update();



		// draw tilemap
		tmr.setView(camera);
		tmr.render();

		// draw world
		b2dr.render(world, camera.combined);

		// draw player
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		player.render(spriteBatch);

		// draw crystalArray

		for (Crystal c : crystalArray) {

			c.render(spriteBatch);
		}

		// draw HUD
		spriteBatch.setProjectionMatrix(hudCam.combined);
		hud.render(spriteBatch);


		// draw box2d world
		if (debug) {
			b2dCam.position.set(camera.position.x / PPM, camera.position.y / PPM, 0f);
			b2dCam.update();
			b2dr.render(world, b2dCam.combined);
		}

		spriteBatch.end();
	}

	@Override
	public void dispose() {

	}

	private void createPlayer() {

		// create player
		BodyDef bDef = new BodyDef();
		bDef.position.set(START_X / PPM, START_Y / PPM);
		bDef.type = BodyDef.BodyType.DynamicBody;
		bDef.fixedRotation = true;

		bDef.linearVelocity.set(PLAYER_SPEED, 0);

		// create body from bodyDef
		Body body = world.createBody(bDef);

		// create box shape for player collisions
		PolygonShape shape = new PolygonShape();
		shape.setAsBox((PlAYER_TEXTURE_SIZE / 2) / PPM, (PlAYER_TEXTURE_SIZE / 2) / PPM);

		// create fixtureDef for player collisions
		FixtureDef fDef = new FixtureDef();
		fDef.shape = shape;
		fDef.density = 1;
		fDef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fDef.filter.maskBits = B2DVars.BIT_RED | B2DVars.BIT_CRYSTAL;

		// create player collision box fixture
		body.createFixture(fDef).setUserData("Player");
		shape.dispose();


		// create box shape for player Foot
		shape = new PolygonShape();
		shape.setAsBox(PlAYER_TEXTURE_SIZE / 2 / PPM, 3 / PPM, new Vector2(0, -(PlAYER_TEXTURE_SIZE / 2) / PPM), 0);
		fDef.shape = shape;
		fDef.isSensor = true;
		fDef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fDef.filter.maskBits = B2DVars.BIT_RED;

		// create player foot fixture
		body.createFixture(fDef).setUserData("foot");
		shape.dispose();

		//create player
		MassData massData = body.getMassData();
		massData.mass = 1;
		body.setMassData(massData);
		player = new Player(body);
		body.setUserData(player);
	}


	private void createTiles() {
		// load tile map

		tiledMap = new TmxMapLoader().load(TILE_MAP_LEVEL);
		tmr = new OrthogonalTiledMapRenderer(tiledMap);
		tileSize = tiledMap.getProperties().get("tilewidth", Integer.class);

		TiledMapTileLayer layer;

		layer = (TiledMapTileLayer) tiledMap.getLayers().get("red");
		createLayer(layer, B2DVars.BIT_RED);
		layer = (TiledMapTileLayer) tiledMap.getLayers().get("green");
		createLayer(layer, B2DVars.BIT_GREEN);
		layer = (TiledMapTileLayer) tiledMap.getLayers().get("blue");
		createLayer(layer, B2DVars.BIT_BLUE);
	}

	private void createLayer(TiledMapTileLayer layer, short bits) {

		BodyDef bDef = new BodyDef();
		FixtureDef fDef = new FixtureDef();

		// go through all cells in layer. to create box2d body for all blocks.
		for (int row = 0; row < layer.getHeight(); row++) {
			for (int col = 0; col < layer.getWidth(); col++) {

				// get cell.
				TiledMapTileLayer.Cell cell = layer.getCell(col, row);

				if (cell == null) {
					continue;
				}
				if (cell.getTile() == null) {
					continue;
				}

				// create a body + fixture from cell
				bDef.type = BodyDef.BodyType.StaticBody;
				bDef.position.set(
								(col + 0.5f) * tileSize / PPM,
								(row + 0.5f) * tileSize / PPM
				);

				ChainShape chainShape = new ChainShape();
				Vector2[] v = new Vector2[3];

				// Bottom left corner
				v[0] = new Vector2(
								-tileSize / 2 / PPM, -tileSize / 2 / PPM);

				// top left corner
				v[1] = new Vector2(
								-tileSize / 2 / PPM, tileSize / 2 / PPM);

				// top right corner
				v[2] = new Vector2(
								tileSize / 2 / PPM, tileSize / 2 / PPM);
				chainShape.createChain(v);
				fDef.friction = 0;
				fDef.shape = chainShape;
				fDef.filter.categoryBits = bits;
				fDef.filter.maskBits = B2DVars.BIT_PLAYER;
				fDef.isSensor = false;
				world.createBody(bDef).createFixture(fDef);
				chainShape.dispose();
			}
		}
	}

	private void createCrystals() {
		crystalArray = new Array<Crystal>();
		MapLayer mapLayer = tiledMap.getLayers().get("crystals");

		BodyDef bDef = new BodyDef();
		FixtureDef fDef = new FixtureDef();
		bDef.type = BodyDef.BodyType.StaticBody;
		float x;
		float y;
		for (MapObject mo : mapLayer.getObjects()) {

			x = Float.parseFloat(mo.getProperties().get("x").toString()) / PPM;
			y = Float.parseFloat(mo.getProperties().get("y").toString()) / PPM;

			bDef.position.set(x, y);
			CircleShape circleShape = new CircleShape();
			circleShape.setRadius(8 / PPM);
			fDef.shape = circleShape;
			fDef.isSensor = true;
			fDef.filter.categoryBits = B2DVars.BIT_CRYSTAL;
			fDef.filter.maskBits = B2DVars.BIT_PLAYER;

			Body body = world.createBody(bDef);
			body.createFixture(fDef).setUserData("Crystal");

			Crystal crystal = new Crystal(body);
			body.setUserData(crystal);
			crystalArray.add(crystal);

			circleShape.dispose();
		}
	}
}
