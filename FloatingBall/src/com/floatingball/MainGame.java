package com.floatingball;

import helper.AssetLoader;

import com.badlogic.gdx.Game;

public class MainGame extends Game {

    @Override
    public void create() {
        AssetLoader.load();
        setScreen(new GameScreen());
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
    
}
