package com.floatingball.gameobjects;

import com.badlogic.gdx.math.Vector2;

public class Ball {
    
    public static final float GRAVITY = 1.7f;
    public static final float MAX_SPEED = 1;
    
    public static final int BALL_WIDTH = 10;
    public static final int BALL_HEIGHT = 10;

    private int gameHeight;
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private int width = BALL_WIDTH;
    private int height = BALL_HEIGHT;

    public Ball(float x, float y, int gameHeight) {
        reset(x, y);
        this.gameHeight = gameHeight;
    }
    
    public void reset(float x, float y) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, GRAVITY*gameHeight);
    }

    public void update(float delta) {
        velocity.add(acceleration.cpy().scl(delta));
        velocity.y = Math.max(-MAX_SPEED*gameHeight, Math.min(MAX_SPEED*gameHeight, velocity.y));
        position.add(velocity.cpy().scl(delta));
    }

    public void onClick(int direction) {
        acceleration.y = GRAVITY*gameHeight * direction;
    }

    public float getX() {
        return position.x;
    }

    public void setY(float y) {
        position.y = y;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}