package com.floatingball;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "FloatingBall";
		cfg.useGL20 = false;
//		cfg.width = Utils.GAME_WIDTH;
//		cfg.height = Utils.GAME_HEIGHT;
		cfg.width = 200;
		cfg.height = 310;
		
		new LwjglApplication(new MainGame(), cfg);
	}
}
