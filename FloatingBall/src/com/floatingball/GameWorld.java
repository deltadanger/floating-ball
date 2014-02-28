package com.floatingball;

import helper.AssetLoader;
import helper.ClickableZone;
import helper.Translate;
import helper.Utils;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.floatingball.comunication.ConfirmParameter;
import com.floatingball.comunication.ISocialNetworkAPI;
import com.floatingball.gameobjects.Ball;
import com.floatingball.gameobjects.Cloud;
import com.floatingball.gameobjects.Scrollable;
import com.floatingball.gameobjects.Spike;

public class GameWorld {
    public static final int BALL_POSITION = 10;
    public static final int MIN_DISTANCE_BETWEEN_CLOUDS = 10;
    public static final int RAND_DISTANCE_BETWEEN_CLOUDS = 30;
    
    public static final int DISTANCE_BETWEEN_SPIKES = 50;
    public static final int NUMBER_OF_SPIKES_BETWEEN_FULL_LINE = 3;
    
    public static final float MAX_POSSIBLE_DELTA = .15f;
    
    public static final String PREFERENCES_NAME = "com.floatingball";
    public static final String PREFERENCE_KEY_HIGH_SCORE = "highScore";
    public static final String PREFERENCE_KEY_MUSIC = "music";
    public static final String PREFERENCE_KEY_SOUND = "sound";
    public static final String PREFERENCE_KEY_SPEED = "speed";
    public static final int BASE_SCROLL_SPEED = -172;
    public static final float SPEED_POWER_FACTOR = 1.03f;
    public static final int DEFAULT_SPEED = 5;
    public static final int MIN_SPEED = 1;
    public static final int MAX_SPEED = 9;
    
    public static enum GameState {
    	START,
    	RUNNING,
    	GAME_OVER
    }
    
    private float scrollSpeed;
    
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
	private int speed = DEFAULT_SPEED;
	private boolean isMusicOn = true;
	private boolean isSoundOn = true;

    private ClickableZone speedIncreaseBtn = new ClickableZone();
    private ClickableZone speedDecreaseBtn = new ClickableZone();
    private ClickableZone soundBtn = new ClickableZone();
    private ClickableZone musicBtn = new ClickableZone();
    private ClickableZone facebookBtn = new ClickableZone();
    private ClickableZone twitterBtn = new ClickableZone();
    
    private ISocialNetworkAPI facebook;
    private ISocialNetworkAPI twitter;

    public GameWorld(int gameHeight, ISocialNetworkAPI facebook, ISocialNetworkAPI twitter) {
    	this.gameHeight = gameHeight;
    	this.facebook = facebook;
    	this.twitter = twitter;
    	
        ball = new Ball(BALL_POSITION, 0, gameHeight);
        
        initialisePreferences();
        
        if (isMusicOn()) {
            AssetLoader.music.loop();
        }
        
        reset();
        currentState = GameState.START;
    }
    
    private void initialisePreferences() {
        boolean flush = false;
        preferences = Gdx.app.getPreferences(PREFERENCES_NAME);
        if (!preferences.contains(PREFERENCE_KEY_HIGH_SCORE)) {
        	preferences.putInteger(PREFERENCE_KEY_HIGH_SCORE, 0);
            flush = true;
        }

        if (!preferences.contains(PREFERENCE_KEY_MUSIC)) {
        	preferences.putBoolean(PREFERENCE_KEY_MUSIC, true);
            flush = true;
        }

        if (!preferences.contains(PREFERENCE_KEY_SOUND)) {
        	preferences.putBoolean(PREFERENCE_KEY_SOUND, true);
            flush = true;
        }

        if (!preferences.contains(PREFERENCE_KEY_SPEED)) {
        	preferences.putInteger(PREFERENCE_KEY_SPEED, DEFAULT_SPEED);
        	flush = true;
        }
        
        if (flush) {
            preferences.flush();
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
        
    	scrollSpeed = (int)(BASE_SCROLL_SPEED * Math.pow(SPEED_POWER_FACTOR, speed));
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

	public void onClick(int x, int y) {
	    switch (currentState) {
	    
	    case START:
            if (soundBtn.isInside(x, y)) {
                setSoundOn(!isSoundOn);
                
            } else if (musicBtn.isInside(x, y)) {
                setMusicOn(!isMusicOn);
                
            } else if (speedIncreaseBtn.isInside(x, y)) {
                setSpeed(Math.min(MAX_SPEED, speed+1));
                
            } else if (speedDecreaseBtn.isInside(x, y)) {
                setSpeed(Math.max(MIN_SPEED, speed-1));
                
            } else {
                reset();
                currentState = GameState.RUNNING;
            }
            break;
            
	    case GAME_OVER:
	        String status = Translate.t(Translate.STATUS_UPDATE).replace(Translate.SCORE_PLACEHOLDER, ""+getHighScore());
	        String message = Translate.t(Translate.STATUS_UPDATE_CONFIRM).replace(Translate.STATUS_PLACEHOLDER, status);
	        
            if (facebookBtn.isInside(x, y)) {
                facebook.updateStatus(status,
                        Translate.t(Translate.APP_URL),
                        Translate.t(Translate.STATUS_UPDATE_SUCCESS),
                        Translate.t(Translate.STATUS_UPDATE_FAILURE),
                        new ConfirmParameter(
                                Translate.t(Translate.STATUS_UPDATE_CONFIRM_TITLE),
                                message.replace(Translate.SOCIAL_NETWORK_PLACEHOLDER, "Facebook"),
                                Translate.t(Translate.YES),
                                Translate.t(Translate.NO)));
                
            } else if (twitterBtn.isInside(x, y)) {
                twitter.updateStatus(status,
                        Translate.t(Translate.APP_URL),
                        Translate.t(Translate.STATUS_UPDATE_SUCCESS),
                        Translate.t(Translate.STATUS_UPDATE_FAILURE),
                        new ConfirmParameter(
                                Translate.t(Translate.STATUS_UPDATE_CONFIRM_TITLE),
                                message.replace(Translate.SOCIAL_NETWORK_PLACEHOLDER, "Twitter"),
                                Translate.t(Translate.YES),
                                Translate.t(Translate.NO)));
                
            } else {
	            reset();
	            currentState = GameState.START;
            }
            break;
            
	    case RUNNING:
            gravityDirection = 0 - gravityDirection;
            ball.onClick(gravityDirection);
            
            Sound s = AssetLoader.gravityDown;
            if (gravityDirection > 0) {
                s = AssetLoader.gravityUp;
            }
            playSound(s);
            break;
	    }
    }
    
    private void generateClouds() {
        int cloudIndex = r.nextInt(AssetLoader.clouds.size());
        Cloud cloud = new Cloud(r.nextInt(gameHeight), AssetLoader.clouds.get(cloudIndex), scrollSpeed);
        
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
                playSound(AssetLoader.hit);
    			return;
    		}
    	}
    	
    	if (ball.getY() < 0 || ball.getY()+ball.getHeight() > gameHeight) {
    		currentState = GameState.GAME_OVER;
			setHighScore();
			playSound(AssetLoader.hit);
    	}
    }
    
    public void playSound(Sound s) {
        if (isSoundOn()) {
            s.play();
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

    private void setMusicOn(boolean isMusicOn) {
        this.isMusicOn = isMusicOn;
        preferences.putBoolean(PREFERENCE_KEY_MUSIC, isMusicOn);
        preferences.flush();
        
        if (isMusicOn) {
            AssetLoader.music.loop();
        } else {
            AssetLoader.music.stop();
        }
    }

    public boolean isSoundOn() {
        return isSoundOn;
    }

    private void setSoundOn(boolean isSoundOn) {
        this.isSoundOn = isSoundOn;
        preferences.putBoolean(PREFERENCE_KEY_SOUND, isSoundOn);
        preferences.flush();
    }

    public int getSpeed() {
        return speed;
    }

    private void setSpeed(int speed) {
        this.speed = speed;
        preferences.putInteger(PREFERENCE_KEY_SPEED, speed);
        preferences.flush();
    }
    

    public void updateSpeedIncreaseBtn(int x, int y, int width, int height) {
        speedIncreaseBtn.update(x, y, width, height);
    }
    
    public void updateSpeedDecreaseBtn(int x, int y, int width, int height) {
        speedDecreaseBtn.update(x, y, width, height);
    }
    
    public void updateSoundBtn(int x, int y, int radius) {
        soundBtn.update(x, y, radius);
    }
    
    public void updateMusicBtn(int x, int y, int radius) {
        musicBtn.update(x, y, radius);
    }
    
    public void updateFacebookBtn(int x, int y, int width, int height) {
        facebookBtn.update(x, y, width, height);
    }
    
    public void updateTwitterBtn(int x, int y, int width, int height) {
        twitterBtn.update(x, y, width, height);
    }
}
