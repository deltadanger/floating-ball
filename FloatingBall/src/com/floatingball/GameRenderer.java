package com.floatingball;

import helper.AssetLoader;
import helper.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.floatingball.gameobjects.Cloud;
import com.floatingball.gameobjects.Scrollable;

public class GameRenderer {

	public static final int SCORE_POSITION_X = 10;
	public static final int SCORE_POSITION_Y = 10;
	
	public static final int CLOUD_SIZE_FACTOR = 2;

	public static final String STRING_TITLE = "Floating Ball";
	public static final String STRING_SPEED = "Speed";
	public static final String STRING_GAME= "Game";
	public static final String STRING_OVER= "Over";
	public static final String STRING_SCORE = "Score:";
	public static final String STRING_HIGH_SCORE = "High Score:";

	public static final int PARAM_POSITION_X = 15;
	public static final int PARAM_POSITION_Y = 15;
	public static final int PARAM_ICON_SIZE = 15;
	public static final int PARAM_ARROW_SIZE = 10;
	public static final int PARAM_PADDING = 10;
	public static final int SPEED_VERTICAL_PADDING = 3;
	public static final int TITLE_POSITION_PERCENT = 20;
	public static final int PLAY_SIZE = 80;
	public static final int TITLE_VERTICAL_PADDING = 15;
	public static final int EXPLANATIONS_WIDTH = 60;
	public static final int GAME_OVER_POSITION_PERCENT = 20;
	public static final int SHARE_ICON_SIZE = 20;
	public static final int SHARE_ICON_PADDING = 10;

    private GameWorld world;
    private OrthographicCamera cam;
    private SpriteBatch batcher;
    
    private int gameHeight;
    
    public GameRenderer(GameWorld world, int gameHeight) {
        this.world = world;
        this.gameHeight = gameHeight;
        cam = new OrthographicCamera();
        cam.setToOrtho(true, Utils.GAME_WIDTH, gameHeight);
        
        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);
    }

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        
        batcher.begin();
        batcher.disableBlending();
        batcher.draw(AssetLoader.background, 0, 0, Utils.GAME_WIDTH, gameHeight);
        batcher.enableBlending();

        switch (world.getCurrentState()) {
			case START:
				renderStart();
				break;
        	case RUNNING:
        		renderRunning();
        		break;
			case GAME_OVER:
				renderGameOver();
				break;
			default:
				break;
        }
        
        batcher.end();
    }
    
    private void renderRunning() {
        // Draw spikes and clouds
    	for (Scrollable s : world.getScrollables()) {
    		if (s instanceof Cloud) {
    			batcher.draw(s.getTexture(), s.getX(), s.getY(), s.getWidth()*CLOUD_SIZE_FACTOR, s.getHeight()*CLOUD_SIZE_FACTOR);
    		} else {
    			batcher.draw(s.getTexture(), s.getX(), s.getY());
    		}
    	}
    	
    	// Draw ball
        batcher.draw(AssetLoader.ball, world.getBall().getX(), world.getBall().getY());
    	
        // Draw gravity arrow
//        TextureRegion arrow = AssetLoader.arrowGravityDown;
//        if (world.getGravityDirection() < 0) {
//        	arrow = AssetLoader.arrowGravityUp;
//        }
//        batcher.draw(arrow, Utils.GAME_WIDTH-arrow.getRegionWidth()-10, 10);
        
        // Draw Score
        String score = ""+world.getScore();
        TextBounds b = AssetLoader.mainFont.getBounds(score);
        AssetLoader.mainFont.draw(batcher, score, Utils.GAME_WIDTH - b.width - SCORE_POSITION_X, SCORE_POSITION_Y);
    }
    
    private void renderStart() {
    	
    	TextureRegion sound = AssetLoader.soundOn;
    	if (!world.isSoundOn()) {
    		sound = AssetLoader.soundOff;
    	}
    	int xPos = Utils.GAME_WIDTH - PARAM_POSITION_X - PARAM_ICON_SIZE;
    	batcher.draw(sound, xPos, PARAM_POSITION_Y, PARAM_ICON_SIZE, PARAM_ICON_SIZE);
    	
    	TextureRegion music = AssetLoader.musicOn;
    	if (!world.isMusicOn()) {
    		music = AssetLoader.musicOff;
    	}
    	xPos = Utils.GAME_WIDTH - PARAM_POSITION_X - 2*PARAM_ICON_SIZE - PARAM_PADDING;
    	batcher.draw(music, xPos, PARAM_POSITION_Y, PARAM_ICON_SIZE, PARAM_ICON_SIZE);

    	String speed = ""+world.getSpeed();
    	Vector2 speedValueBounds = new Vector2(AssetLoader.secondaryFont.getBounds(speed).width, -AssetLoader.secondaryFont.getBounds(speed).height);
    	xPos = Utils.GAME_WIDTH - PARAM_POSITION_X - 2*PARAM_ICON_SIZE - 2*PARAM_PADDING - (int)speedValueBounds.x;
    	AssetLoader.secondaryFont.draw(batcher, speed, xPos, PARAM_POSITION_Y);
    	
    	xPos = Utils.GAME_WIDTH - PARAM_POSITION_X - 2*PARAM_ICON_SIZE - 2*PARAM_PADDING - (int)(speedValueBounds.x/2) - PARAM_ARROW_SIZE/2;
    	int yPos = PARAM_POSITION_Y - SPEED_VERTICAL_PADDING - PARAM_ARROW_SIZE;
    	batcher.draw(AssetLoader.arrowUp, xPos, yPos, PARAM_ARROW_SIZE, PARAM_ARROW_SIZE);
    	
    	yPos = PARAM_POSITION_Y + (int)speedValueBounds.y + SPEED_VERTICAL_PADDING;
    	batcher.draw(AssetLoader.arrowDown, xPos, yPos, PARAM_ARROW_SIZE, PARAM_ARROW_SIZE);

    	Vector2 speedTextBounds = new Vector2(AssetLoader.secondaryFont.getBounds(STRING_SPEED).width, -AssetLoader.secondaryFont.getBounds(STRING_SPEED).height);
    	xPos = Utils.GAME_WIDTH - PARAM_POSITION_X - 2*PARAM_ICON_SIZE - 3*PARAM_PADDING - (int)speedValueBounds.x - (int)speedTextBounds.x;
    	AssetLoader.secondaryFont.draw(batcher, STRING_SPEED, xPos, PARAM_POSITION_Y);
    	
    	yPos = gameHeight*TITLE_POSITION_PERCENT/100;
    	TextBounds b = AssetLoader.mainFont.getBounds(STRING_TITLE);
    	AssetLoader.mainFont.draw(batcher, STRING_TITLE, Utils.GAME_WIDTH/2 - (int)(b.width/2), yPos);
    	
    	yPos += -b.height + TITLE_VERTICAL_PADDING;
    	batcher.draw(AssetLoader.play, Utils.GAME_WIDTH/2 - PLAY_SIZE/2, yPos, PLAY_SIZE, PLAY_SIZE);
    	
    	yPos += PLAY_SIZE + TITLE_VERTICAL_PADDING;
    	int height = AssetLoader.explanations.getRegionHeight() * EXPLANATIONS_WIDTH / AssetLoader.explanations.getRegionWidth();
    	batcher.draw(AssetLoader.explanations, Utils.GAME_WIDTH/2 - EXPLANATIONS_WIDTH/2, yPos, EXPLANATIONS_WIDTH, height);
    }
    
    private void renderGameOver() {
    	renderRunning();
    	
    	int yPos = gameHeight*GAME_OVER_POSITION_PERCENT/100;
    	TextBounds b = AssetLoader.gameOverFont.getBounds(STRING_GAME);
    	AssetLoader.gameOverFont.drawMultiLine(batcher, STRING_GAME, Utils.GAME_WIDTH/2 - b.width/2, yPos);

    	yPos += (-b.height) + TITLE_VERTICAL_PADDING;
    	b = AssetLoader.gameOverFont.getBounds(STRING_OVER);
    	AssetLoader.gameOverFont.draw(batcher, STRING_OVER, Utils.GAME_WIDTH/2 - b.width/2, yPos);

    	yPos += (-b.height) + 2*TITLE_VERTICAL_PADDING;
    	String score = STRING_SCORE + " " + world.getScore();
    	b = AssetLoader.secondaryFont.getBounds(score);
    	AssetLoader.secondaryFont.draw(batcher, score, Utils.GAME_WIDTH/2 - b.width/2, yPos);
    	
    	yPos += (-b.height) + TITLE_VERTICAL_PADDING;
    	String highScore = STRING_HIGH_SCORE + " " + world.getHighScore();
    	b = AssetLoader.secondaryFont.getBounds(highScore);
    	AssetLoader.secondaryFont.draw(batcher, highScore, Utils.GAME_WIDTH/2 - b.width/2, yPos);
    	
    	yPos += (-b.height) + TITLE_VERTICAL_PADDING;
    	batcher.draw(AssetLoader.facebook, Utils.GAME_WIDTH/2 - SHARE_ICON_PADDING/2 - SHARE_ICON_SIZE, yPos, SHARE_ICON_SIZE, SHARE_ICON_SIZE);
    	batcher.draw(AssetLoader.twitter, Utils.GAME_WIDTH/2 + SHARE_ICON_PADDING/2, yPos, SHARE_ICON_SIZE, SHARE_ICON_SIZE);
    }
}
