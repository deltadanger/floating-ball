package com.floatingball.gameobjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.floatingball.GameRenderer;

public class Cloud extends Scrollable {

	public Cloud(float y, TextureRegion texture, float scrollSpeed) {
		super(y, texture, scrollSpeed);

		width = (int)(texture.getRegionWidth() * GameRenderer.CLOUD_SIZE_FACTOR);
		height = (int)(texture.getRegionHeight() * GameRenderer.CLOUD_SIZE_FACTOR);
	}

	@Override
    public float getTailX() {
        return position.x + width*GameRenderer.CLOUD_SIZE_FACTOR;
    }

}
