package com.floatingball;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Floating Ball";
		cfg.useGL20 = false;
		cfg.width = 200;
		cfg.height = 300;
		
		new LwjglApplication(new MainGame(), cfg);
	}
}
