package com.tc2r.tc2r.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.tc2r.tc2r.entities.Player;
import com.tc2r.tc2r.handlers.B2DVars;
import com.tc2r.tc2r.handlers.GameStateManager;
import com.tc2r.tc2r.handlers.MyContactListener;
import com.tc2r.tc2r.handlers.MyInput;
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


	private MyContactListener cl;
	private Player player;

	private TiledMap tiledMap;
	private float tileSize;
	private OrthogonalTiledMapRenderer tmr;




	public Play(GameStateManager gameStateManager) {
		super(gameStateManager);

		//Setup Box2D
		world = new World(new Vector2(0, -9.81f), true);
		cl = new MyContactListener();
		world.setContactListener(cl);
		b2dr = new Box2DDebugRenderer();

		// Create Player
		createPlayer();

		// Create Tiles
		createTiles();

		// set up box2d Cam
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, myGame.V_Width / PPM, myGame.V_Height/ PPM);
}



	@Override
	public void handleInput() {

		if(MyInput.isPressed(MyInput.BUTTON1) && cl.isPlayerOnGround()){
			System.out.println("Pressed Z");

			player.getBody().applyForceToCenter(0, 200, true);
		}
//
//		if(MyInput.isDown(MyInput.BUTTON2)){
//			System.out.println("HOLD X");
//		}

	}

	@Override
	public void update(float delta) {

		handleInput();
		world.step(delta, 6, 2);
		player.update(delta);
	}

	@Override
	public void render() {

		// clear screen
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// draw tilemap
		tmr.setView(camera);
		tmr.render();

		// draw world
		b2dr.render(world, camera.combined);

		// draw player
		spriteBatch.setProjectionMatrix(camera.combined);
		player.render(spriteBatch);

		// draw box2d world
		b2dr.render(world, b2dCam.combined);


	}

	@Override
	public void dispose() {

	}
	private void createPlayer() {

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();

		// create player
		bdef.position.set(160 /PPM, 200 /PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		bdef.linearVelocity.set(1, 0);
		Body body = world.createBody(bdef);

		shape.setAsBox(13/ PPM, 13/ PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits = B2DVars.BIT_RED | B2DVars.BIT_BLUE | B2DVars.BIT_GREEN;

		body.createFixture(fdef).setUserData("Player");

		// create foot sensor
		shape.setAsBox(13/PPM, 2 / PPM, new Vector2(0, -13 / PPM), 0);
		fdef.shape = shape;
		fdef.isSensor = true;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits = B2DVars.BIT_RED | B2DVars.BIT_BLUE | B2DVars.BIT_GREEN;

		body.createFixture(fdef).setUserData("foot");

		//create player

		player = new Player(body);
		body.setUserData(player);

		shape.dispose();

	}


	private void createTiles() {
		// load tile map

		tiledMap = new TmxMapLoader().load("maps/test.tmx");
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

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		// go through all cells in layer. to create box2d body for all blocks.
		for(int row = 0; row < layer.getHeight(); row++){
			for(int col = 0; col < layer.getWidth(); col++){

				// get cell.
				TiledMapTileLayer.Cell cell = layer.getCell(col, row);

				if(cell == null){
					continue;
				}
				if(cell.getTile() == null){
					continue;
				}

				// create a body + fixture from cell
				bdef.type = BodyDef.BodyType.StaticBody;
				bdef.position.set(
								(col + 0.5f) * tileSize / PPM,
								(row + 0.5f) * tileSize / PPM
				);

				ChainShape cs = new ChainShape();
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
				cs.createChain(v);
				fdef.friction = 0;
				fdef.shape = cs;
				fdef.filter.categoryBits = bits;
				fdef.filter.maskBits = B2DVars.BIT_PLAYER;
				fdef.isSensor = false;
				world.createBody(bdef).createFixture(fdef);



			}
		}

	}
}
