package helper;

import com.badlogic.gdx.InputProcessor;
import com.floatingball.Ball;

public class InputHandler implements InputProcessor {
    private Ball ball;

    public InputHandler(Ball ball) {
        this.ball = ball;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        ball.onClick();
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}

