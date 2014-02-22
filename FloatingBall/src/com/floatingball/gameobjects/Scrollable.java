package com.floatingball.gameobjects;

import helper.Utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Scrollable {
    protected Vector2 position;
    protected Vector2 velocity;
    protected int width;
    protected int height;
    protected TextureRegion texture;

    public Scrollable(float y, TextureRegion texture, float scrollSpeed) {
        position = new Vector2(Utils.GAME_WIDTH + texture.getRegionWidth(), y);
        velocity = new Vector2(scrollSpeed, 0);
        this.width = texture.getRegionWidth();
        this.height = texture.getRegionHeight();
        this.texture = texture;
    }

    public void update(float delta) {
        position.add(velocity.cpy().scl(delta));
    }

    public float getTailX() {
        return position.x + width;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public TextureRegion getTexture() {
        return texture;
    }
}
