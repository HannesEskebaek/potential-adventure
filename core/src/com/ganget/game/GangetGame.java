package com.ganget.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class GangetGame extends Game {
	
	public SpriteBatch batch;
	public BitmapFont font;
	public World world;
	public Box2DDebugRenderer debugRenderer;

	@Override
	public void create() {
		batch = new SpriteBatch();
		//Use LibGDX's default Arial font.
        font = new BitmapFont();
        world = new World(new Vector2(0, -40), true);
        debugRenderer = new Box2DDebugRenderer();
        
        this.setScreen(new GameScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}	

	@Override
	public void dispose() {	
		batch.dispose();
		font.dispose();
	}
}
