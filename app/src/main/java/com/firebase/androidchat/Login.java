package com.firebase.androidchat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class Login extends AppCompatActivity {
    private static final String TAG = "LaunchScreen";

    EditText userName;
    EditText password;
    Button enter;
    String name;
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_launch_screen);
            userName = (EditText) findViewById(R.id.userName);
            enter = (Button) findViewById(R.id.enter);
            password = (EditText) findViewById(R.id.pass);
            if(isLollipop()) {
                Window window = getWindow();
                window.setStatusBarColor(Color.parseColor("#12579B"));
            }


        }

    boolean isLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public void loginOnClick(View v) {
        name = userName.getText().toString();
        pass = password.getText().toString();
        if(name.length() < 4 && pass.length() < 6)
            System.out.println("Something has fucked up");
        else {
            ParseUser.logInInBackground(name, pass, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(user != null) {
                        startActivity(new Intent(Login.this, UserList.class));
                    }
                    else {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    public void toRegisterOnClick(View v) {
        startActivity(new Intent(Login.this, Register.class));
    }



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
