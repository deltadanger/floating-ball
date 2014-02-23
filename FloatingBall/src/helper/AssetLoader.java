package helper;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

    public static Texture texture;
    public static TextureRegion background;
    public static TextureRegion ball;
    public static TextureRegion spike;
//    public static TextureRegion arrowGravityUp;
//    public static TextureRegion arrowGravityDown;
    public static ArrayList<TextureRegion> clouds = new ArrayList<TextureRegion>();
    
    public static Texture homeTexture;
    public static TextureRegion musicOn;
    public static TextureRegion musicOff;
    public static TextureRegion soundOn;
    public static TextureRegion soundOff;
    public static TextureRegion arrowUp;
    public static TextureRegion arrowDown;
    public static TextureRegion play;
    public static TextureRegion explanations;
    public static TextureRegion facebook;
    public static TextureRegion twitter;

    public static BitmapFont mainFont;
    public static BitmapFont gameOverFont;
    public static BitmapFont secondaryFont;
    

    public static void load() {

    	// Images
    	
        texture = new Texture(Gdx.files.internal("data/textures.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        background = new TextureRegion(texture, 0, 0, 1, 1);
        background.flip(false, true);

        ball = new TextureRegion(texture, 1, 0, 10, 10);
        ball.flip(false, true);

        spike = new TextureRegion(texture, 11, 0, 15, 15);
        spike.flip(false, true);

//        arrowGravityUp = new TextureRegion(texture, 100, 0, 13, 7);
//        arrowGravityUp.flip(false, true);
//
//        arrowGravityDown = new TextureRegion(texture, 100, 7, 13, 7);
//        arrowGravityDown.flip(false, true);

        TextureRegion cloud = new TextureRegion(texture, 26, 0, 27, 13);
        cloud.flip(false, true);
        clouds.add(cloud);

        cloud = new TextureRegion(texture, 53, 0, 24, 15);
        cloud.flip(false, true);
        clouds.add(cloud);

        cloud = new TextureRegion(texture, 77, 0, 23, 13);
        cloud.flip(false, true);
        clouds.add(cloud);

        cloud = new TextureRegion(texture, 0, 15, 29, 16);
        cloud.flip(false, true);
        clouds.add(cloud);

        cloud = new TextureRegion(texture, 29, 13, 22, 13);
        cloud.flip(false, true);
        clouds.add(cloud);

        cloud = new TextureRegion(texture, 51, 15, 18, 10);
        cloud.flip(false, true);
        clouds.add(cloud);

        cloud = new TextureRegion(texture, 71, 15, 21, 10);
        cloud.flip(false, true);
        clouds.add(cloud);

        cloud = new TextureRegion(texture, 0, 32, 20, 13);
        cloud.flip(false, true);
        clouds.add(cloud);

        cloud = new TextureRegion(texture, 20, 31, 23, 11);
        cloud.flip(false, true);
        clouds.add(cloud);

        cloud = new TextureRegion(texture, 43, 26, 16, 11);
        cloud.flip(false, true);
        clouds.add(cloud);

        cloud = new TextureRegion(texture, 59, 26, 17, 13);
        cloud.flip(false, true);
        clouds.add(cloud);

        cloud = new TextureRegion(texture, 77, 26, 22, 14);
        cloud.flip(false, true);
        clouds.add(cloud);

        cloud = new TextureRegion(texture, 43, 39, 24, 11);
        cloud.flip(false, true);
        clouds.add(cloud);

        cloud = new TextureRegion(texture, 71, 40, 26, 9);
        cloud.flip(false, true);
        clouds.add(cloud);
        

        homeTexture = new Texture(Gdx.files.internal("data/home_textures.png"));
        homeTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        explanations = new TextureRegion(homeTexture, 36, 593, 582, 477);
        explanations.flip(false, true);

        play = new TextureRegion(homeTexture, 713, 605, 596, 596);
        play.flip(false, true);

        arrowUp = new TextureRegion(homeTexture, 0, 0, 308, 268);
        arrowUp.flip(false, true);

        arrowDown = new TextureRegion(homeTexture, 0, 275, 308, 268);
        arrowDown.flip(false, true);

        musicOn = new TextureRegion(homeTexture, 418, 320, 256, 256);
        musicOn.flip(false, true);

        musicOff = new TextureRegion(homeTexture, 737, 308, 256, 256);
        musicOff.flip(false, true);

        soundOn = new TextureRegion(homeTexture, 675, 22, 256, 256);
        soundOn.flip(false, true);

        soundOff = new TextureRegion(homeTexture, 371, 14, 256, 256);
        soundOff.flip(false, true);

        facebook = new TextureRegion(homeTexture, 1526, 36, 512, 512);
        facebook.flip(false, true);

        twitter = new TextureRegion(homeTexture, 1018, 36, 512, 512);
        twitter.flip(false, true);

        
        // Fonts

        mainFont = new BitmapFont(Gdx.files.internal("data/bd_cartoon.fnt"));
        mainFont.setScale(.6f, -.6f);
        gameOverFont = new BitmapFont(Gdx.files.internal("data/bd_cartoon.fnt"));
        gameOverFont.setScale(1f, -1f);
        secondaryFont = new BitmapFont(Gdx.files.internal("data/dunkirk.fnt"));
        secondaryFont.setScale(.5f, -.5f);
        
        // Sounds
        
        
//        sound = Gdx.audio.newSound(Gdx.files.internal("data/sound.wav"));
//        AssetLoader.sound.play();


//        TextureRegion[] birds = { birdDown, bird, birdUp };
//        birdAnimation = new Animation(0.06f, birds);
//        birdAnimation.setPlayMode(Animation.LOOP_PINGPONG);
    }
    
    public static void dispose() {
        texture.dispose();
        
        // Dispose sounds
    }

}