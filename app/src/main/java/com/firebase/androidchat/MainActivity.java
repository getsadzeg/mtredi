package com.firebase.androidchat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.vdurmont.emoji.EmojiParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    boolean b;
    Toolbar toolbar;
    String resultDecimal = null;

    private static final String FIREBASE_URL = "https://dove-hacktb.firebaseio.com/";
    private static final String TAG = "MainActivity";
    private String mUsername;
    private ChatListAdapter mChatListAdapter;
    private GoogleApiClient client;
    SharedPreferences prefs;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getApplication().getSharedPreferences("ChatPrefs", 0);
        mUsername = prefs.getString("username", "");
        if(mUsername.equals("")) {
            finish();
            startActivity(new Intent(this, LaunchScreen.class));
        } else {
            setContentView(R.layout.activity_main);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("მიმოწერა");
            setSupportActionBar(toolbar);
            listView = (ListView) findViewById(R.id.listd);
            listView.setDivider(null);
            listView.setDividerHeight(0);
            toolbar.setBackgroundColor(Color.parseColor("#1667B7"));
            // Make sure we have a mUsername
            theme();
            setupUsername();

            // Setup our Firebase mFirebaseRef
            inputText = (EditText) findViewById(R.id.messageInput);
            // Setup our input methods. Enter key on the keyboard or pushing the send button
            EditText inputText = (EditText) findViewById(R.id.messageInput);
            inputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                    if (actionId == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                        sendMessage();
                    }
                    return true;
                }
            });

            findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendMessage();
                }
            });

            client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        }
    }

    void theme () {
        if(isLollipop()) {
            Window window = getWindow();
            window.setStatusBarColor(Color.parseColor("#12579B"));
        }
    }

    boolean isLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    @Override
    public void onStart() {
        super.onStart();



    }

    @Override
    public void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_send) {
            prefs.edit().putString("username", "").commit();
            startActivity(new Intent(this, LaunchScreen.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupUsername() {
        if (mUsername == null) {
            // Assign a random user name if we don't have one saved.
            mUsername = "";
            prefs.edit().putString("username", mUsername).commit();
        }
    }

    public void showDialog(Activity activity, String title, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        if (title != null) builder.setTitle(title);

        builder.setMessage(message);
        builder.setPositiveButton("თანხმობა", null);
        builder.show();
    }

    EditText inputText;

    private void sendMessage() {
        String output = "";
        String input = inputText.getText().toString();
        resultDecimal = EmojiParser.parseToHtmlDecimal(input);
        String[] array = input.split(" ");
        Gegram gram = new Parse(array);
        if(!gram.parseBarbarism().equals("no mistake")) {
            System.out.println("Barbarism's index is: " + gram.returnIndex(array));
            array[gram.returnIndex(array)] = gram.returnMatcher();
            System.out.println(gram.parseBarbarism());
        }
        for (int i = 0; i < array.length; i++) {
            System.out.println("array[0]: " + array[0]);
        }
        // System.out.println(resultDecimal);
        //System.out.println(resultHexadecimal);
        Pattern p = Pattern.compile("[^ა-ჰ0-9\\t\\n .,/<>?;:\"'`~!@#$€%^&*()_+=|\\\\-]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(resultDecimal);
        b = m.find();
        if (!input.equals("") && !b) {

            for (int i = 0; i < array.length; i++) {
                output += array[i] + " ";
            }


            Conversation chat = new Conversation(output, mUsername);
            System.out.println("output is: " + output);

            /*mFirebaseRef.push().setValue(chat, new Firebase.CompletionListener() {
                @Override
                public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                    Log.d(TAG, "onComplete() called with: " + "firebaseError = [" + firebaseError + "], firebase = [" + firebase + "]");
                    inputText.setText("");
                }
            });*/

        } else if (b) {
            showDialog(this, "მდგომარეობა", "შეტყობინება არ გაიგზავნა, რადგან თქვენ უცხო ენაზე აკრიფეთ ტექსტი");
            /*dialog = new Dialog(this, "მდგომარეობა", "შეტყობინება არ გაიგზავნა, რადგან თქვენ უცხო ენაზე აკრიფეთ ტექსტი");
            dialog.show();*/
            System.out.println("Not sent");
        }
    }
}