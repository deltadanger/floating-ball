package com.floatingball;
import helper.InputHandler;
import helper.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {
    
    private GameWorld world;
    private GameRenderer renderer;
    
    // This is the constructor, not the class declaration
    public GameScreen() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();      
        int gameWidth = Utils.GAME_WIDTH;
        int gameHeight = (int) (screenHeight / (screenWidth / gameWidth));
        
        world = new GameWorld(gameHeight / 2);
        renderer = new GameRenderer(world);
        
        Gdx.input.setInputProcessor(new InputHandler(world.getBall()));
    }

    @Override
    public void render(float delta) {
        world.update(delta);
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("GameScreen - resizing");
    }

    @Override
    public void show() {
        System.out.println("GameScreen - show called");
    }

    @Override
    public void hide() {
        System.out.println("GameScreen - hide called");     
    }

    @Override
    public void pause() {
        System.out.println("GameScreen - pause called");        
    }

    @Override
    public void resume() {
        System.out.println("GameScreen - resume called");       
    }

    @Override
    public void dispose() {
        // Leave blank
    }

}