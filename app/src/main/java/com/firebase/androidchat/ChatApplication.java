package com.firebase.androidchat;

import android.app.Application;
import com.parse.Parse;

public class ChatApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);

        Parse.initialize(this);
    }
}
