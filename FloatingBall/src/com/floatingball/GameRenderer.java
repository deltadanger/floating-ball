package com.floatingball;

import helper.AssetLoader;
import helper.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameRenderer {

    private GameWorld world;
    private OrthographicCamera cam;
    private SpriteBatch batcher;
    
    private int gameHeight;
    
    public GameRenderer(GameWorld world, int gameHeight) {
        this.world = world;
        this.gameHeight = gameHeight;
        cam = new OrthographicCamera();
        cam.setToOrtho(true, Utils.GAME_WIDTH/2, gameHeight/2);
        
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
        
        batcher.draw(AssetLoader.ball, world.getBall().getX(),
                                       world.getBall().getY(),
                                       world.getBall().getWidth(),
                                       world.getBall().getHeight());
        
        batcher.end();
    }

}
