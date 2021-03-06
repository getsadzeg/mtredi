package com.firebase.androidchat;


import java.util.Date;

public class Conversation {

    private String message;
    private String author;
    private Date date;
    protected final static int STATUS_SENDING = 0;
    protected final static int STATUS_SENT = 1;
    protected final static int STATUS_FAILED = 2;
    protected final static int STATUS_SEEN = 3; //3 is just random.
    private int status = STATUS_SENT;
    @SuppressWarnings("unused")
    public Conversation() {
    }

    Conversation(String message, String author, Date date) {
        this.setMessage(message);
        this.setAuthor(author);
        this.setDate(date);
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
    public boolean isSent()
    {
        return UserList.user.getUsername().equals(author);
    }
}
