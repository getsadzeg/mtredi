package com.firebase.androidchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parse.ParseUser;

public class UserAdapter extends BaseAdapter {
    public static String TAG = "UserAdapter";
    public TextView labelname;
    LayoutInflater layoutInflater;

    public UserAdapter(Context context){
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return UserList.userList.size();
    }
    @Override
    public ParseUser getItem(int index) {
        return UserList.userList.get(index);
    }
    @Override
    public long getItemId(int index) {
        return index;
    }
    @Override
    public View getView(int pos, View v, ViewGroup group) {
           ParseUser c = getItem(pos);
            if (v == null) {
                v = layoutInflater.inflate(R.layout.chat_item, null);
            }
            labelname = (TextView) v;
            labelname.setText(c.getUsername());
            labelname.setCompoundDrawablesWithIntrinsicBounds(c.getBoolean("online") ? R.drawable.ic_online
                    : R.drawable.ic_offline, 0, R.drawable.arrow, 0);

        return v;
    }
}
