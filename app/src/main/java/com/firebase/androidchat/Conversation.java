package com.firebase.androidchat;


public class Conversation {

    private String message;
    private String author;
    private final static int STATUS_SENDING = 0;
    private final static int STATUS_SENT = 1;
    private final static int STATUS_FAILED = 2;
    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    public Conversation() {
    }

    Conversation(String message, String author) {
        this.message = message;
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }
}
