package helper;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

    public static Texture texture;
    public static TextureRegion background;
    public static TextureRegion ball;
    public static TextureRegion spike;
    public static ArrayList<TextureRegion> clouds = new ArrayList<TextureRegion>();

    public static void load() {

        texture = new Texture(Gdx.files.internal("data/textures.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        background = new TextureRegion(texture, 0, 0, 1, 1);
        background.flip(false, true);

        ball = new TextureRegion(texture, 1, 0, 10, 10);
        ball.flip(false, true);

        spike = new TextureRegion(texture, 11, 0, 15, 15);
        spike.flip(false, true);

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


//        TextureRegion[] birds = { birdDown, bird, birdUp };
//        birdAnimation = new Animation(0.06f, birds);
//        birdAnimation.setPlayMode(Animation.LOOP_PINGPONG);
    }
    
    public static void dispose() {
        // We must dispose of the texture when we are finished.
        texture.dispose();
    }

}