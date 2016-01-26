package com.firebase.androidchat;


import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ChatAdapter extends BaseAdapter {
    Context context = ChatApplication.getContext();
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
        if(conv.isSent()) {
            if(conv.getStatus() == Conversation.STATUS_SENDING)
                statuslbl.setText("იგზავნება..");
            if(conv.getStatus() == Conversation.STATUS_SENT)
                statuslbl.setText("გაიგზავნა");
            if(conv.getStatus() == Conversation.STATUS_SEEN)
                statuslbl.setText("ნანახია"); //Here we go, probably.
            else statuslbl.setText("არ გაიგზავნა");
        }
        else { // go to isSent implementation. if isSent is false, so message is received, not sent. So look at code:
            statuslbl.setText(""); //we set no text on receiver's chat on received message
            conv.setStatus(Conversation.STATUS_SEEN); //we set STATUS_SEEN
        }

        return v;
    }
}
