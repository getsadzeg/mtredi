package com.firebase.androidchat;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ChatAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    @Override
    public int getCount() {
        return Chat.convList.size();
    }
    @Override
    public Conversation getItem(int index) {
        return Chat.convList.get(index);
    }
    @Override
    public long getItemId(int index) {
        return index;
    }
    @Override
    public View getView(int pos, View v, ViewGroup arg2) {
        Conversation conv = getItem(pos);
        /*if (conv.isSent())
            v = layoutInflater.inflate(R.layout.chat_item_sent, null); // not compiling yet
        else
            v = layoutInflater.inflate(R.layout.chat_item_rcv, null); //same here
            */
        return v;
    }
}
