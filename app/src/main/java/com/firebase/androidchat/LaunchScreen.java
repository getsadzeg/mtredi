package com.firebase.androidchat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class LaunchScreen extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LaunchScreen";

    EditText userName;
    EditText password;
    Button enter;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getApplication().getSharedPreferences("ChatPrefs", 0);
        final String prefsString = prefs.getString("username", "");
        if (!prefsString.equals("")) {
            loginwithUsername(prefsString);
        } else {
            setContentView(R.layout.activity_launch_screen);
            userName = (EditText) findViewById(R.id.userName);
            enter = (Button) findViewById(R.id.enter);

            if(isLollipop()) {
                Window window = getWindow();
                window.setStatusBarColor(Color.parseColor("#12579B"));
            }
            enter.setOnClickListener(this);

        }
    }

    boolean isLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    @Override
    public void onClick(View v) {
        final String mUsername = userName.getText().toString();
        Log.d("firebaseError", "mUsername : " + mUsername);
        if (mUsername.isEmpty())
            return;

    }
    public void toRegisterOnClick(View v) {
        startActivity(new Intent(LaunchScreen.this, Register.class));
    }

    void loginwithUsername(final String x) {


   /* private void check(final Chat chat) {
        ref.child("chat1").push().setValue(chat, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                Log.d(TAG, "onComplete() called with: " + "firebaseError = [" + firebaseError + "], firebase = [" + firebase + "]");
                prefs.edit().putString("username", chat.getAuthor()).commit();
                prefs.edit().putString("message", chat.getMessage()).commit();


            }
        });
        }*/
    }
}
