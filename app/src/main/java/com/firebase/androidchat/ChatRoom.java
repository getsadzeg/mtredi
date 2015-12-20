package com.firebase.androidchat;


public class ChatRoom {

    private String title;
    private String partner;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    public ChatRoom() {
    }

    ChatRoom(String title, String partner) {
        this.title = title;
        this.partner = partner;
    }

    public String getTitle() {
        return title;
    }

    public String getPartner() {
        return partner;
    }
}
