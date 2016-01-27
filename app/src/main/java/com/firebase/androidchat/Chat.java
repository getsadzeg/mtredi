package com.firebase.androidchat;

import android.app.Activity;
import android.app.AlertDialog;
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

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.vdurmont.emoji.EmojiParser;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Chat extends AppCompatActivity {
    boolean b;
    Toolbar toolbar;
    String resultDecimal = null;
    private static final String TAG = "MainActivity";
    /*private String mUsername;
    private ChatListAdapter mChatListAdapter;*/
    ListView listView;
    private ChatAdapter adapter;
    protected static ArrayList<Conversation> convList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            ChatApplication.setContext(this);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle(UserList.nameFromList);
            setSupportActionBar(toolbar);
            listView = (ListView) findViewById(R.id.listd);
            adapter = new ChatAdapter();
            listView.setAdapter(adapter);
            listView.setDivider(null);
            listView.setDividerHeight(0);
            toolbar.setBackgroundColor(Color.parseColor("#1667B7"));
            theme();

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


        return super.onOptionsItemSelected(item);
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
        if (input.trim().length() > 0 && !b) {
            System.out.println("Yeah! not only spaces,yo?");
            for (int i = 0; i < array.length; i++) {
                output += array[i] + " ";
            }


            final Conversation conversation = new Conversation(output, UserList.user.getUsername(), new Date());
            conversation.setStatus(Conversation.STATUS_SENDING);
            convList.add(conversation);
            adapter.notifyDataSetChanged();
            ParseObject chatobj = new ParseObject("Chat");
            chatobj.put("sender", UserList.user.getUsername());
            chatobj.put("receiver", UserList.nameFromList);
            chatobj.put("message", output);
            chatobj.saveEventually(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e == null) {
                        conversation.setStatus(Conversation.STATUS_SENT);
                    }
                    else {
                        conversation.setStatus(Conversation.STATUS_FAILED);
                    }
                    adapter.notifyDataSetChanged();
                }
            });
            System.out.println("output is: " + output);

            /*mFirebaseRef.push().setValue(chat, new Firebase.CompletionListener() {
                @Override
                public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                    Log.d(TAG, "onComplete() called with: " + "firebaseError = [" + firebaseError + "], firebase = [" + firebase + "]");
                    inputText.setText("");
                }
            });*/

        }
        else if (b) {
            showDialog(this, "მდგომარეობა", "შეტყობინება არ გაიგზავნა, რადგან თქვენ უცხო ენაზე აკრიფეთ ტექსტი");
            System.out.println("Not sent");
        }
    }
    private void loadConversation() {
        ParseQuery<ParseObject> chatquery = new ParseQuery<ParseObject>("Chat");
        if(convList.size() == 0) {
            ArrayList<String> chatl = new ArrayList<>();
            chatquery.whereContainedIn("sender", chatl);
            chatquery.whereContainedIn("receiver", chatl);
        }
    }
}