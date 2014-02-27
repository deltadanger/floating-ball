package com.floatingball;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.facebook.Session;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = false;
        
        initialize(new MainGame(new FacebookAPI(this), new TwitterAPI(this)), cfg);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("test", "MainActivity: onActivityResult " + Session.getActiveSession());
        if (Session.getActiveSession() != null) {
            Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
        }
    }
}