package com.firebase.androidchat;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.firebase.client.Firebase;

@SuppressWarnings("deprecation")
public class ConversationCreate extends Activity {
    private static final String TAG = "ConversationCreate";
    FloatingActionButton fab;


    private Firebase chatRooms;
    private static final String FIREBASE_URL = "https://dove-hacktb.firebaseio.com";
    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_create);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        SharedPreferences prefs = getApplication().getSharedPreferences("ChatPrefs", 0);
        mUsername = prefs.getString("username", null);
        chatRooms = new Firebase(FIREBASE_URL).child("chatRooms");


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    public void showDialog(){
        new MaterialDialog.Builder(this)
                .title("Select chat partner")
                .items()
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        Log.d(TAG, "onSelection() called with: " + "dialog = [" + dialog + "], view = [" + view + "], which = [" + which + "], text = [" + text + "]");
                        /**
                         * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected radio button to actually be selected.
                         **/
                        return true;
                    }
                })
                .positiveText("Start chat")
                .neutralText("Nevermind")
                .show();
    }

}
