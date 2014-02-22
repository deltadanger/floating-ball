package com.floatingball;

import helper.AssetLoader;
import helper.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.floatingball.gameobjects.Scrollable;

public class GameRenderer {

	public static final int SCORE_POSITION_X = 10;
	public static final int SCORE_POSITION_Y= 10;

    private GameWorld world;
    private OrthographicCamera cam;
    private SpriteBatch batcher;
    
    private int gameHeight;
    
    public GameRenderer(GameWorld world, int gameHeight) {
        this.world = world;
        this.gameHeight = gameHeight;
        cam = new OrthographicCamera();
        cam.setToOrtho(true, Utils.GAME_WIDTH, gameHeight);
        
        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);
    }

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        
        batcher.begin();
        batcher.disableBlending();
        batcher.draw(AssetLoader.background, 0, 0, Utils.GAME_WIDTH, gameHeight);
        batcher.enableBlending();

        // Draw spikes and clouds
    	for (Scrollable s : world.getScrollables()) {
    		batcher.draw(s.getTexture(), s.getX(), s.getY());
    	}
    	
    	// Draw ball
        batcher.draw(AssetLoader.ball, world.getBall().getX(), world.getBall().getY());
    	
        // Draw gravity arrow
//        TextureRegion arrow = AssetLoader.arrowGravityDown;
//        if (world.getGravityDirection() < 0) {
//        	arrow = AssetLoader.arrowGravityUp;
//        }
//        batcher.draw(arrow, Utils.GAME_WIDTH-arrow.getRegionWidth()-10, 10);
        
        // Draw Score
        String score = ""+world.getScore();
        TextBounds b = AssetLoader.mainFont.getBounds(score);
        AssetLoader.mainFont.draw(batcher, score, Utils.GAME_WIDTH - b.width - SCORE_POSITION_X, SCORE_POSITION_Y);
        
        batcher.end();
    }
}
