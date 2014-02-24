package com.floatingball;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.floatingball.interfaces.FacebookAPI;
import com.floatingball.interfaces.TwitterAPI;

public class Main implements FacebookAPI, TwitterAPI{
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Floating Ball";
		cfg.useGL20 = false;
		cfg.width = 400;
		cfg.height = 600;
		
		new LwjglApplication(new MainGame(new Main(), new Main()), cfg);
	}

	@Override
	public void updateStatus(String status) {
		System.out.println(status);
	}
}
