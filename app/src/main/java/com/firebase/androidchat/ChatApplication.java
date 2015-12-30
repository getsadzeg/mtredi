package com.firebase.androidchat;

import com.parse.Parse;

public class ChatApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);

        Parse.initialize(this);
    }
}
