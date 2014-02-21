package com.floatingball;

public class GameWorld {
    public static int BALL_POSITION = 30;
    
    private Ball ball;

    public GameWorld(int gameHeight) {
        ball = new Ball(BALL_POSITION, gameHeight/2 - Ball.BALL_HEIGHT/2);
    }

    public void update(float delta) {
        ball.update(delta);
    }

    public Ball getBall() {
        return ball;
    }
}
