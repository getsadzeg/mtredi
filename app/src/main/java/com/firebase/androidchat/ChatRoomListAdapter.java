package com.firebase.androidchat;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;


public class ChatRoomListAdapter extends FirebaseListAdapter<ChatRoom> {

    // The mUsername for this client. We use this to indicate which messages originated from this user
    private String mUsername;

    public ChatRoomListAdapter(Query ref, Activity activity, int layout, String mUsername) {
        super(ref, ChatRoom.class, layout, activity);
        this.mUsername = mUsername;
    }

    /**
     * Bind an instance of the <code>Chat</code> class to our view. This method is called by <code>FirebaseListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>Chat</code> instance that represents the current data to bind.
     *
     * @param view A view instance corresponding to the layout we passed to the constructor.
     * @param chatRoom An instance representing the current state of a chat room
     */
    @Override
    protected void populateView(View view, ChatRoom chatRoom) {
        // Map a Chat object to an entry in our listview
        ((TextView) view.findViewById(R.id.author)).setText(chatRoom.getTitle());
        ((TextView) view.findViewById(R.id.message)).setText(chatRoom.getPartner());
    }
}
