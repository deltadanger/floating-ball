package com.floatingball;

import helper.AssetLoader;
import helper.Utils;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.floatingball.gameobjects.Ball;
import com.floatingball.gameobjects.Scrollable;

public class GameWorld {
    public static final int BALL_POSITION = 10;
    public static final int MIN_DISTANCE_BETWEEN_CLOUDS = 10;
    public static final int RAND_DISTANCE_BETWEEN_CLOUDS = 30;
    
    public static final int DISTANCE_BETWEEN_SPIKES = 40;
    public static final int NUMBER_OF_SPIKES_BETWEEN_FULL_LINE = 3;
    
    public enum GameState {
    	READY,
    	RUNNING,
    	GAME_OVER
    }
    
    private float scrollSpeed = -100f;
    
    private Random r = new Random();
    
    private int gameHeight;
    
    private int gravityDirection;
    private Ball ball;
    
    private Scrollable lastCloud;
    private Scrollable lastSpike;
    private int spikeNumber;
    
    private int score;
    private GameState currentState;

	private ArrayList<Scrollable> scrollables;
	private ArrayList<Scrollable> spikes;

    public GameWorld(int gameHeight, float scrollSpeedFactor) {
    	this.gameHeight = gameHeight;
    	this.scrollSpeed *= scrollSpeedFactor;
        ball = new Ball(BALL_POSITION, 0);
        
        reset();
    }
    
    public void reset() {
    	ball.reset(BALL_POSITION, gameHeight*1/3 - Ball.BALL_HEIGHT/2);
        gravityDirection = 1;
        spikeNumber = 0;
        score = 0;
        
    	scrollables = new ArrayList<Scrollable>();
    	spikes = new ArrayList<Scrollable>();
    	
    	lastCloud = null;
    	lastSpike = null;
    	
        currentState = GameState.READY;
    }

    public void update(float delta) {
    	if (GameState.GAME_OVER.equals(currentState)) {
    		reset();
    		return;
    	}
    	
    	if (!GameState.RUNNING.equals(currentState)) {
    		return;
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
			if (spikes.contains(s)) {
				spikes.remove(s);
				spikeLeftScreen = true;
			}
		}
		if (spikeLeftScreen) {
			score++;
		}
		
		checkCollision();
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

	public void onClick() {
		if (GameState.READY.equals(currentState)) {
			currentState = GameState.RUNNING;
			return;
		}
		
		if (GameState.RUNNING.equals(currentState)) {
	    	gravityDirection = 0 - gravityDirection;
	    	ball.onClick(gravityDirection);
		}
    }
    
    private void generateClouds() {
    	Scrollable cloud = new Scrollable(r.nextInt(gameHeight), AssetLoader.clouds.get(r.nextInt(AssetLoader.clouds.size())), scrollSpeed);
    	
    	if (lastCloud == null) {
			scrollables.add(cloud);
			lastCloud = cloud;
			
    	} else if (Utils.GAME_WIDTH - lastCloud.getX()-lastCloud.getWidth() > MIN_DISTANCE_BETWEEN_CLOUDS + r.nextInt(RAND_DISTANCE_BETWEEN_CLOUDS)) {
			scrollables.add(cloud);
			lastCloud = cloud;
    	}
    }
    
    private void generateSpikes() {
    	Scrollable spike;
    	
    	if (lastSpike == null) {
			spike = new Scrollable(ball.getY(), AssetLoader.spike, scrollSpeed);
			scrollables.add(spike);
			spikes.add(spike);
			lastSpike = spike;
			spikeNumber = 1;
			
    	} else if (Utils.GAME_WIDTH - lastSpike.getX()-lastSpike.getWidth() > DISTANCE_BETWEEN_SPIKES){
    		if (spikeNumber > NUMBER_OF_SPIKES_BETWEEN_FULL_LINE) {
    			generateFullLine();
    			spikeNumber = 0;
    		} else {
    			spike = new Scrollable(ball.getY(), AssetLoader.spike, scrollSpeed);
    			scrollables.add(spike);
    			spikes.add(spike);
    			lastSpike = spike;
    			spikeNumber++;
    		}
    	}
    }
    
    private void generateFullLine() {
    	int y = 0;
    	Scrollable spike = null;
    	while (y < gameHeight) {
    		// Do not add spike at ball's Y, plus the ones above and below
    		spike = new Scrollable(y, AssetLoader.spike, scrollSpeed);
//    		if (y-2*spike.getHeight() > ball.getY() || ball.getY() > y+spike.getHeight()) { // space of 3 spikes
        	if (y-spike.getHeight() > ball.getY() || ball.getY() > y+spike.getHeight()) { // space of 2 spikes
	    		scrollables.add(spike);
				spikes.add(spike);
    		}
			
			y += spike.getHeight();
    	}
		lastSpike = spike;
    }
    
    private void checkCollision() {
    	Vector2 ballCenter = new Vector2(ball.getX() + ball.getWidth()/2, ball.getY() + ball.getHeight()/2);
    	for (Scrollable spike : spikes) {
    		Vector2 spikeCenter = new Vector2(spike.getX() + spike.getWidth()/2, spike.getY() + spike.getHeight()/2);
    		if (spikeCenter.dst(ballCenter) < ball.getWidth()/2 + spike.getWidth()/2) {
    			currentState = GameState.GAME_OVER;
    			return;
    		}
    	}
    	
    	if (ball.getY() < 0 || ball.getY()+ball.getHeight() > gameHeight) {
    		currentState = GameState.GAME_OVER;
    	}
    }
}
