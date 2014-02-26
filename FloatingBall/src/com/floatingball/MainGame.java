package com.floatingball;

import helper.AssetLoader;

import com.badlogic.gdx.Game;
import com.floatingball.comunication.IFacebookAPI;
import com.floatingball.comunication.ITwitterAPI;

public class MainGame extends Game {
    
    private IFacebookAPI facebook;
    private ITwitterAPI twitter;
    
    public MainGame(IFacebookAPI facebook, ITwitterAPI twitter) {
        this.facebook = facebook;
        this.twitter = twitter;
    }

	@Override
    public void create() {
        AssetLoader.load();
        setScreen(new GameScreen(facebook, twitter));
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
    
}
