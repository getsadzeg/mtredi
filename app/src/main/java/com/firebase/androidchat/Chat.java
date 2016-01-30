package com.firebase.androidchat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.vdurmont.emoji.EmojiParser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    public ChatAdapter adapter;
    private Date lastmsgDate;
    public static ArrayList<Conversation> convList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ChatApplication.setContext(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(UserList.nameFromList);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.listd);
        convList = new ArrayList<Conversation>();
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

    void theme() {
        if (isLollipop()) {
            Window window = getWindow();
            window.setStatusBarColor(Color.parseColor("#12579B"));
        }
    }

    boolean isLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadConversation();
    }

    @Override
    public void onPause() {
        super.onPause();
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
        if (!gram.parseBarbarism().equals("no mistake")) {
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
                    if (e == null) {
                        conversation.setStatus(Conversation.STATUS_SENT);
                    } else {
                        System.out.println("ATTENTION! STATUS_FAILED");
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

        } else if (b) {
            showDialog(this, "მდგომარეობა", "შეტყობინება არ გაიგზავნა, რადგან თქვენ უცხო ენაზე აკრიფეთ ტექსტი");
            System.out.println("Not sent");
        }
    }

    private void loadConversation() {
        ParseQuery<ParseObject> chatquery = new ParseQuery<ParseObject>("Chat");
        if (convList.size() == 0) {
            ArrayList<String> chatl = new ArrayList<>();
            chatquery.whereContainedIn("sender", chatl);
            chatquery.whereContainedIn("receiver", chatl);
        } else {
            if (lastmsgDate != null) {
                chatquery.whereGreaterThan("createdAt", lastmsgDate);
                chatquery.whereEqualTo("sender", UserList.user.getUsername());
                chatquery.whereEqualTo("receiver", UserList.nameFromList);
            }
        }
        chatquery.orderByDescending("createdAt");
        chatquery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects != null && objects.size() > 0) {
                    for (int i = objects.size() - 1; i >= 0; i--) {
                        ParseObject pobject = objects.get(i);
                        Conversation c = new Conversation(pobject.get("message").toString(),
                                pobject.get("sender").toString(), pobject.getCreatedAt());
                        convList.add(c);
                        if (lastmsgDate == null || lastmsgDate.before(c.getDate())) {
                            lastmsgDate = c.getDate();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private class ChatAdapter extends BaseAdapter {
        Context context = ChatApplication.getContext();
        LayoutInflater layoutInflater = getLayoutInflater(); //assigning added

        @Override
        public int getCount() {
            return convList.size();
        }

        @Override
        public Conversation getItem(int index) {
            return convList.get(index);
        }

        @Override
        public long getItemId(int index) {
            return index;
        }

        @Override
        public View getView(int pos, View v, ViewGroup arg2) {
            Conversation conv = getItem(pos);
            if (conv.isSent())
                v = layoutInflater.inflate(R.layout.chat_item_sent, null);
            else
                v = layoutInflater.inflate(R.layout.chat_item_rcv, null);
            TextView label = (TextView) v.findViewById(R.id.lbl1);
            label.setText(DateUtils.getRelativeDateTimeString(context, conv
                            .getDate().getTime(), DateUtils.SECOND_IN_MILLIS,
                    DateUtils.DAY_IN_MILLIS, 0));
            TextView textlbl = (TextView) v.findViewById(R.id.lbl2);
            textlbl.setText(conv.getMessage());
            TextView statuslbl = (TextView) v.findViewById(R.id.lbl3);
            if (conv.isSent()) {
                if (conv.getStatus() == Conversation.STATUS_SENDING)
                    statuslbl.setText("იგზავნება..");
                else if (conv.getStatus() == Conversation.STATUS_SENT)
                    statuslbl.setText("გაიგზავნა");
                else if (conv.getStatus() == Conversation.STATUS_SEEN)
                    statuslbl.setText("ნანახია"); //Here we go, probably.
                else {
                    System.out.println("WTF! " +  conv.isSent());
                    statuslbl.setText("არ გაიგზავნა");
                }
            } else { // go to isSent implementation. if isSent is false, so message is received, not sent. So look at code:
                statuslbl.setText(""); //we set no text on receiver's chat on received message
                conv.setStatus(Conversation.STATUS_SEEN); //we set STATUS_SEEN
            }

            return v;
        }
    }
}