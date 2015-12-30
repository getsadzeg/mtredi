package com.firebase.androidchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class Register extends AppCompatActivity {
    private EditText userName;
    private EditText password;
    private EditText Mail;
    private String name;
    private String pass;
    private String mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        Mail = (EditText) findViewById(R.id.Mail);
    }
    public void registerOnClick(View v) {
        name = userName.getText().toString();
        pass = password.getText().toString();
        mail = Mail.getText().toString();
        if(name.length() < 4 || pass.length() < 6 || mail.length() == 0)
            System.out.println("Something has fucked up");
        final ParseUser user = new ParseUser();
        user.setUsername(name);
        user.setEmail(mail);
        user.setPassword(pass);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null) {
                    startActivity(new Intent(Register.this, UserList.class));
                }
                else e.printStackTrace();
            }
        });
    }
}
