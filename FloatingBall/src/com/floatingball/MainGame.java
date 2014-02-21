package com.floatingball;

import com.badlogic.gdx.Game;

public class MainGame extends Game {

    @Override
    public void create() {
        setScreen(new GameScreen());
    }

    @Override
    public void dispose() {
        super.dispose();
//        AssetLoader.dispose();
    }
    
}
