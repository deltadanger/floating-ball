package com.floatingball;

import helper.Utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameRenderer {

    private GameWorld world;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    
    public GameRenderer(GameWorld world) {
        this.world = world;
        cam = new OrthographicCamera();
        cam.setToOrtho(true, Utils.GAME_WIDTH/2, Utils.GAME_HEIGHT/2);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);
    }

    public void render() {
    }

}
