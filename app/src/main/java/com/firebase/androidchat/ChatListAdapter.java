package com.firebase.androidchat;

public class ChatListAdapter  {

    // The mUsername for this client. We use this to indicate which messages originated from this user
    /*private String mUsername;

    public ChatListAdapter(Query ref, Activity activity, int layout, String mUsername) {
        super(ref, Conversation.class, layout, activity);
        this.mUsername = mUsername;
    }


    @Override
    protected void populateView(View view, Conversation chat) {
        // Map a Chat object to an entry in our listview
        String author = chat.getAuthor();
        TextView authorText = (TextView) view.findViewById(R.id.author);
        authorText.setText(author);
        // If the message was sent by this user, color it differently
        if (author != null && author.equals(mUsername)) {
            authorText.setTextColor(Color.RED);
        } else {
            authorText.setTextColor(Color.BLUE);
        }
        ((TextView) view.findViewById(R.id.message)).setText(chat.getMessage());
    } */
}
