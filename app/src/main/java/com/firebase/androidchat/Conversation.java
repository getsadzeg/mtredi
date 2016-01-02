package com.firebase.androidchat;


import java.util.Date;

public class Conversation {

    private String message;
    private String author;
    private Date date;
    private final static int STATUS_SENDING = 0;
    private final static int STATUS_SENT = 1;
    private final static int STATUS_FAILED = 2;
    private int status = STATUS_SENT;
    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    public Conversation() {
    }

    Conversation(String message, String author) {
        this.setMessage(message);
        this.setAuthor(author);
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
