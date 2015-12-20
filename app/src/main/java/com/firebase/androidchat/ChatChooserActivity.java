package com.firebase.androidchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.client.Firebase;

public class ChatChooserActivity extends AppCompatActivity {

    private Firebase mFirebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_chooser);
        mFirebaseRef = new Firebase(getString(R.string.firebase_url)).child("user");
    }
}
