package com.floatingball.gameobjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.floatingball.GameRenderer;

public class Spike extends Scrollable {

	public Spike(float y, TextureRegion texture, float scrollSpeed) {
		super(y, texture, scrollSpeed);

		width = GameRenderer.SPIKE_SIZE;
		height = GameRenderer.SPIKE_SIZE;
	}
}
