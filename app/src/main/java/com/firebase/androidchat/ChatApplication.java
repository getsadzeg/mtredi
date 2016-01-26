package com.firebase.androidchat;

import android.app.Application;
import android.content.Context;

import com.parse.Parse;

public class ChatApplication extends Application {
    private static Context mContext;
    public static void setContext(Context context) {
        ChatApplication.mContext = context;
    }
    public static Context getContext() {
        return mContext;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this);

    }
}
