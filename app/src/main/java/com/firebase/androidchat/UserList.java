package com.firebase.androidchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UserList extends AppCompatActivity {
    public static ArrayList<ParseUser> userList;
    public static String TAG = "UserList";
    public static String nameFromList = "";
    public static ParseUser user = ParseUser.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        updateUserStatus(true);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        updateUserStatus(false);
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadUserList();
    }
    private void updateUserStatus(boolean isOnline) {
        user.put("online", isOnline);
        user.saveInBackground();
        //System.out.println("getBoolean's result : " + user.getBoolean("online"));
    }
    private void loadUserList() {
        ParseUser.getQuery().whereNotEqualTo("username", user.getUsername()).findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (objects != null) {
                    if (objects.size() == 0) System.out.println("No user found");
                    userList = new ArrayList<>(objects);
                    ListView list = (ListView) findViewById(R.id.userList);
                    list.setAdapter(new UserAdapter(UserList.this));
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            nameFromList = userList.get(i).getUsername();
                            System.out.println("name from list:" +  nameFromList);
                            startActivity(new Intent(UserList.this, Chat.class));
                            finish();
                        }
                    });
                }
                else {
                    System.out.println("exception detected while loading user list");
                    e.printStackTrace();
                }
            }
        });
    }
}

