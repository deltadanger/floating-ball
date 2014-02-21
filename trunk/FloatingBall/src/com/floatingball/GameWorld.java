package com.floatingball;

public class GameWorld {
    private Ball ball;

    public GameWorld(int midPointY) {
        ball = new Ball(33, midPointY - 5, 17, 12);
    }

    public void update(float delta) {
        ball.update(delta);
    }

    public Ball getBall() {
        return ball;

    }

}
