package com.floatingball;

import helper.AssetLoader;

import com.badlogic.gdx.Game;
import com.floatingball.interfaces.FacebookAPI;
import com.floatingball.interfaces.TwitterAPI;

public class MainGame extends Game {
	
	private FacebookAPI facebook;
	private TwitterAPI twitter;

    public MainGame(FacebookAPI facebook, TwitterAPI twitter) {
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
