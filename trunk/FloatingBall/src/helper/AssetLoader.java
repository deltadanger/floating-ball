package helper;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

    public static Texture texture;
    public static TextureRegion background;
    public static TextureRegion ball;
    public static TextureRegion spike;
    public static ArrayList<TextureRegion> clouds;
    
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

    public static Sound music;
    public static Sound gravityDown;
    public static Sound gravityUp;
    public static Sound hit;

    public static BitmapFont mainFont;
    public static BitmapFont gameOverFont;
    public static BitmapFont secondaryFont;
    

    public static void load() {

    	// Images
    	
        texture = new Texture(Gdx.files.internal("data/textures.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        background = new TextureRegion(texture, 0, 0, 1, 1);
        background.flip(false, true);

        ball = new TextureRegion(texture, 1, 1, 254, 254);
        ball.flip(false, true);

        spike = new TextureRegion(texture, 271, 0, 351, 351);
        spike.flip(false, true);

        clouds = new ArrayList<TextureRegion>();
        
        TextureRegion cloud = new TextureRegion(texture, 694, 30, 242, 103);
        cloud.flip(false, true);
        clouds.add(cloud);

        cloud = new TextureRegion(texture, 713, 153, 226, 102);
        cloud.flip(false, true);
        clouds.add(cloud);

        cloud = new TextureRegion(texture, 716, 265, 221, 121);
        cloud.flip(false, true);
        clouds.add(cloud);

        cloud = new TextureRegion(texture, 6, 394, 332, 154);
        cloud.flip(false, true);
        clouds.add(cloud);

        cloud = new TextureRegion(texture, 377, 418, 213, 108);
        cloud.flip(false, true);
        clouds.add(cloud);

        cloud = new TextureRegion(texture, 718, 402, 223, 109);
        cloud.flip(false, true);
        clouds.add(cloud);

        cloud = new TextureRegion(texture, 714, 556, 213, 112);
        cloud.flip(false, true);
        clouds.add(cloud);

        cloud = new TextureRegion(texture, 344, 552, 291, 122);
        cloud.flip(false, true);
        clouds.add(cloud);

        cloud = new TextureRegion(texture, 43, 574, 244, 117);
        cloud.flip(false, true);
        clouds.add(cloud);

        cloud = new TextureRegion(texture, 48, 714, 217, 105);
        cloud.flip(false, true);
        clouds.add(cloud);

        cloud = new TextureRegion(texture, 337, 706, 239, 108);
        cloud.flip(false, true);
        clouds.add(cloud);

        cloud = new TextureRegion(texture, 680, 704, 221, 109);
        cloud.flip(false, true);
        clouds.add(cloud);

        cloud = new TextureRegion(texture, 779, 827, 180, 95);
        cloud.flip(false, true);
        clouds.add(cloud);

        cloud = new TextureRegion(texture, 545, 821, 182, 101);
        cloud.flip(false, true);
        clouds.add(cloud);

        cloud = new TextureRegion(texture, 302, 845, 181, 81);
        cloud.flip(false, true);
        clouds.add(cloud);

        cloud = new TextureRegion(texture, 71, 852, 172, 78);
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
        mainFont.setScale(.08f, -.08f);
        gameOverFont = new BitmapFont(Gdx.files.internal("data/bd_cartoon.fnt"));
        gameOverFont.setScale(.1f, -.1f);
        secondaryFont = new BitmapFont(Gdx.files.internal("data/dunkirk.fnt"));
        secondaryFont.setScale(.07f, -.07f);
        
        // Sounds

        music = Gdx.audio.newSound(Gdx.files.internal("data/gravity_down.wav"));
        gravityDown = Gdx.audio.newSound(Gdx.files.internal("data/gravity_down.wav"));
        gravityUp = Gdx.audio.newSound(Gdx.files.internal("data/gravity_up.wav"));
        hit = Gdx.audio.newSound(Gdx.files.internal("data/hit.wav"));

//        TextureRegion[] birds = { birdDown, bird, birdUp };
//        birdAnimation = new Animation(0.06f, birds);
//        birdAnimation.setPlayMode(Animation.LOOP_PINGPONG);
    }
    
    public static void dispose() {
        clouds = null;
        
        texture.dispose();
        homeTexture.dispose();
        
        mainFont.dispose();
        gameOverFont.dispose();
        secondaryFont.dispose();
        
        music.dispose();
        gravityDown.dispose();
        gravityUp.dispose();
        hit.dispose();
    }

}