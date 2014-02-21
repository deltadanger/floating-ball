package com.floatingball;

import com.badlogic.gdx.math.Vector2;

public class Ball {
    
    public static int GRAVITY = 460;
    public static int MAX_SPEED = 200;
    
    public static int BALL_WIDTH = 10;
    public static int BALL_HEIGHT = 10;

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private int width = BALL_WIDTH;
    private int height = BALL_HEIGHT;

    public Ball(float x, float y) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, GRAVITY);
    }

    public void update(float delta) {
        velocity.add(acceleration.cpy().scl(delta));
        velocity.y = Math.min(MAX_SPEED, velocity.y);
        position.add(velocity.cpy().scl(delta));
    }

    public void onClick() {
        acceleration.y = 0 - acceleration.y;
    }

    public float getX() {
        return position.x;
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