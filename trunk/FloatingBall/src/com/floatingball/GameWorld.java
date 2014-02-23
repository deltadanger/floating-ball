package com.floatingball;

import helper.AssetLoader;
import helper.Utils;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.floatingball.gameobjects.Ball;
import com.floatingball.gameobjects.Cloud;
import com.floatingball.gameobjects.Scrollable;
import com.floatingball.gameobjects.Spike;

public class GameWorld {
    public static final int BALL_POSITION = 10;
    public static final int MIN_DISTANCE_BETWEEN_CLOUDS = 10;
    public static final int RAND_DISTANCE_BETWEEN_CLOUDS = 30;
    
    public static final int DISTANCE_BETWEEN_SPIKES = 40;
    public static final int NUMBER_OF_SPIKES_BETWEEN_FULL_LINE = 3;
    
    public static final float MAX_POSSIBLE_DELTA = .15f;
    
    public static final String PREFERENCES_NAME = "com.floatingball";
    public static final String PREFERENCE_KEY_HIGH_SCORE = "highScore";
    public static final String PREFERENCE_KEY_MUSIC = "music";
    public static final String PREFERENCE_KEY_SOUND = "sound";
    public static final String PREFERENCE_KEY_SPEED = "speed";
    public static final int DEFAULT_SPEED = 5;
    
    public static enum GameState {
    	START,
    	RUNNING,
    	GAME_OVER
    }
    
    private float scrollSpeed = -100f;
    
    private Random r = new Random();
    
    private int gameHeight;
    
    private int gravityDirection;
    private Ball ball;
    
    private Cloud lastCloud;
    private Spike lastSpike;
    private int spikeNumber;
    
    private int score;
    private GameState currentState;

	private ArrayList<Scrollable> scrollables;
	
	private Preferences preferences;
	private int speed;
	private boolean isMusicOn = true;
	private boolean isSoundOn = true;

    public GameWorld(int gameHeight, float scrollSpeedFactor) {
    	this.gameHeight = gameHeight;
    	this.scrollSpeed *= scrollSpeedFactor;
        ball = new Ball(BALL_POSITION, 0);
        
        initialisePreferences();
        
        reset();
        currentState = GameState.START;
    }
    
    private void initialisePreferences() {
        preferences = Gdx.app.getPreferences(PREFERENCES_NAME);
        if (!preferences.contains(PREFERENCE_KEY_HIGH_SCORE)) {
        	preferences.putInteger(PREFERENCE_KEY_HIGH_SCORE, 0);
        }

        if (!preferences.contains(PREFERENCE_KEY_MUSIC)) {
        	preferences.putBoolean(PREFERENCE_KEY_MUSIC, true);
        }

        if (!preferences.contains(PREFERENCE_KEY_SOUND)) {
        	preferences.putBoolean(PREFERENCE_KEY_SOUND, true);
        }

        if (!preferences.contains(PREFERENCE_KEY_SPEED)) {
        	preferences.putInteger(PREFERENCE_KEY_SPEED, DEFAULT_SPEED);
        }

        speed = preferences.getInteger(PREFERENCE_KEY_SPEED);
        isMusicOn = preferences.getBoolean(PREFERENCE_KEY_MUSIC);
        isSoundOn = preferences.getBoolean(PREFERENCE_KEY_SOUND);
    }
    
    public void reset() {
    	ball.reset(BALL_POSITION, gameHeight*1/3 - Ball.BALL_HEIGHT/2);
        gravityDirection = 1;
        spikeNumber = 0;
        score = 0;
        
    	scrollables = new ArrayList<Scrollable>();
    	
    	lastCloud = null;
    	lastSpike = null;
    }

    public void update(float delta) {
    	if (!GameState.RUNNING.equals(currentState)) {
    		return;
    	}

        if (delta > MAX_POSSIBLE_DELTA) {
            delta = MAX_POSSIBLE_DELTA;
        }
    	
        ball.update(delta);
        
        generateClouds();
        generateSpikes();
        
        ArrayList<Scrollable> toRemove = new ArrayList<Scrollable>();
		for (Scrollable s : scrollables) {
			s.update(delta);
			if (s.getTailX() < 0) {
				toRemove.add(s);
			}
		}
		
		boolean spikeLeftScreen = false;
		for (Scrollable s : toRemove) {
			scrollables.remove(s);
			if (s instanceof Spike) {
				spikeLeftScreen = true;
			}
		}
		if (spikeLeftScreen) {
			score++;
		}
		
		checkCollision();
    }

	public void onClick() {
		if (GameState.START.equals(currentState)) {
	        reset();
	        currentState = GameState.RUNNING;
			return;
		}
    	if (GameState.GAME_OVER.equals(currentState)) {
            reset();
            currentState = GameState.START;
    		return;
    	}
    	
		
		if (GameState.RUNNING.equals(currentState)) {
	    	gravityDirection = 0 - gravityDirection;
	    	ball.onClick(gravityDirection);
		}
    }
    
    private void generateClouds() {
    	Cloud cloud = new Cloud(r.nextInt(gameHeight), AssetLoader.clouds.get(r.nextInt(AssetLoader.clouds.size())), scrollSpeed);
    	
    	if (lastCloud == null) {
			scrollables.add(cloud);
			lastCloud = cloud;
			
    	} else if (Utils.GAME_WIDTH - lastCloud.getX()-lastCloud.getWidth() > MIN_DISTANCE_BETWEEN_CLOUDS + r.nextInt(RAND_DISTANCE_BETWEEN_CLOUDS)) {
			scrollables.add(cloud);
			lastCloud = cloud;
    	}
    }
    
    private void generateSpikes() {
    	Spike spike;
    	
    	if (lastSpike == null) {
			spike = new Spike(ball.getY(), AssetLoader.spike, scrollSpeed);
			scrollables.add(spike);
			lastSpike = spike;
			spikeNumber = 1;
			
    	} else if (Utils.GAME_WIDTH - lastSpike.getX()-lastSpike.getWidth() > DISTANCE_BETWEEN_SPIKES){
    		if (spikeNumber > NUMBER_OF_SPIKES_BETWEEN_FULL_LINE) {
    			generateFullLine();
    			spikeNumber = 0;
    		} else {
    			spike = new Spike(ball.getY(), AssetLoader.spike, scrollSpeed);
    			scrollables.add(spike);
    			lastSpike = spike;
    			spikeNumber++;
    		}
    	}
    }
    
    private void generateFullLine() {
    	int y = 0;
    	Spike spike = null;
    	while (y < gameHeight) {
    		// Do not add spike at ball's Y, plus the ones above and below
    		spike = new Spike(y, AssetLoader.spike, scrollSpeed);
//    		if (y-2*spike.getHeight() > ball.getY() || ball.getY() > y+spike.getHeight()) { // space of 3 spikes
        	if (y-spike.getHeight() > ball.getY() || ball.getY() > y+spike.getHeight()) { // space of 2 spikes
	    		scrollables.add(spike);
    		}
			
			y += spike.getHeight();
    	}
		lastSpike = spike;
    }
    
    private void checkCollision() {
    	Vector2 ballCenter = new Vector2(ball.getX() + ball.getWidth()/2, ball.getY() + ball.getHeight()/2);
    	for (Scrollable spike : scrollables) {
    		if (!(spike instanceof Spike)) {
    			continue;
    		}
    		Vector2 spikeCenter = new Vector2(spike.getX() + spike.getWidth()/2, spike.getY() + spike.getHeight()/2);
    		if (spikeCenter.dst(ballCenter) < ball.getWidth()/2 + spike.getWidth()/2) {
    			currentState = GameState.GAME_OVER;
    			setHighScore();
    			return;
    		}
    	}
    	
    	if (ball.getY() < 0 || ball.getY()+ball.getHeight() > gameHeight) {
    		currentState = GameState.GAME_OVER;
			setHighScore();
    	}
    }
    
    public int getHighScore() {
		return preferences.getInteger(PREFERENCE_KEY_HIGH_SCORE);
    }
    
    private void setHighScore() {
    	if (preferences.getInteger(PREFERENCE_KEY_HIGH_SCORE) < score) {
    		preferences.putInteger(PREFERENCE_KEY_HIGH_SCORE, score);
    		preferences.flush();
    	}
    }


    public Ball getBall() {
        return ball;
    }

    public ArrayList<Scrollable> getScrollables() {
		return scrollables;
	}

	public int getGravityDirection() {
		return gravityDirection;
	}

	public GameState getCurrentState() {
		return currentState;
	}

	public int getScore() {
		return score;
	}

	public boolean isMusicOn() {
		return isMusicOn;
	}

//	public void setMusicOn(boolean isMusicOn) {
//		this.isMusicOn = isMusicOn;
//	}

	public boolean isSoundOn() {
		return isSoundOn;
	}

//	public void setSoundOn(boolean isSoundOn) {
//		this.isSoundOn = isSoundOn;
//	}

	public int getSpeed() {
		return speed;
	}

//	public void setSpeed(int speed) {
//		this.speed = speed;
//	}
}
