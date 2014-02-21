package com.floatingball;
import helper.InputHandler;
import helper.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {
    
    private GameWorld world;
    private GameRenderer renderer;
    
    public GameScreen() {
        int gameHeight = (int) (Utils.GAME_WIDTH * Gdx.graphics.getHeight() / Gdx.graphics.getWidth());
        
        world = new GameWorld(gameHeight);
        renderer = new GameRenderer(world, gameHeight);
        
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
    }

}