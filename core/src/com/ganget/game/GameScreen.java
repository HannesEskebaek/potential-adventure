package com.ganget.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class GameScreen implements Screen {

	final GangetGame game;

	private OrthographicCamera camera;

	// rendering
	private float accumulator = 0;
	private final float TIME_STEP = 1 / 60f;
	private final int VELOCITY_ITERATIONS = 6;
	private final int POSITION_ITERATIONS = 2;

	// shapes
	private CircleShape ball;
	private PolygonShape groundBox;

	public GameScreen(final GangetGame game) {
		this.game = game;

		// create the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		createBodies();
	}

	private void createBodies() {
		// Ground
		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.position.set(new Vector2(0, 10));

		Body groundBody = game.world.createBody(groundBodyDef);

		groundBox = new PolygonShape();	
		groundBox.setAsBox(camera.viewportWidth, 10.0f);
		
		groundBody.createFixture(groundBox, 0.0f);

		// Ball
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(100, 300);

		Body body = game.world.createBody(bodyDef);

		ball = new CircleShape();
		ball.setRadius(6f);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = ball;
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f;

		Fixture fixture = body.createFixture(fixtureDef);
	}

	@Override
	public void show() {
		// start the playback of the background music
		// when the screen is shown
		// rainMusic.play();
	}

	@Override
	public void render(float delta) {
		// clear the screen with a dark blue color. The
		// arguments to glClearColor are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell the camera to update its matrices.
		camera.update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		game.batch.setProjectionMatrix(camera.combined);

		// begin a new batch and draw the bucket and
		// all drops
		// game.batch.begin();

		// game.batch.end();

		// process user input
		// if (Gdx.input.isTouched()) {
		// Vector3 touchPos = new Vector3();
		// touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		// camera.unproject(touchPos);
		// bucket.x = touchPos.x - 64 / 2;
		// }

		game.debugRenderer.render(game.world, camera.combined);
		doPhysicsStep(delta);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		ball.dispose();
		groundBox.dispose();
	}

	private void doPhysicsStep(float deltaTime) {
		// fixed time step
		// max frame time to avoid spiral of death (on slow devices)
		float frameTime = Math.min(deltaTime, 0.25f);
		accumulator += frameTime;
		while (accumulator >= TIME_STEP) {
			game.world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
			accumulator -= TIME_STEP;
		}
	}
}
