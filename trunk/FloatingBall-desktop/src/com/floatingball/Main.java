package com.floatingball;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.floatingball.comunication.ConfirmParameter;
import com.floatingball.comunication.IFacebookAPI;
import com.floatingball.comunication.ITwitterAPI;

public class Main{
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Floating Ball";
		cfg.useGL20 = false;
		cfg.width = 400;
		cfg.height = 600;
		
		new LwjglApplication(new MainGame(new IFacebookAPI() {

            @Override
            public void updateStatus(String status, String url, String success,
                    String failure, ConfirmParameter param) {
                System.out.println("Facebook " + status);
                
            }
        }, new ITwitterAPI() {

            @Override
            public void updateStatus(String status, String url, String success,
                    String failure, ConfirmParameter param) {
                System.out.println("Twitter " + status);
                
            }
        }), cfg);
	}
}
