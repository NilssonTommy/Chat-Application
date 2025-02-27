package com.example;
import java.sql.Timestamp;

public class ImageMessage implements Message {
    private String author;
    private Timestamp timestamp;
    private byte[] img;
    private String chatroom;

    public ImageMessage(String author, String chatroom, byte[] img){
        this.img = img.clone();
        timestamp = new Timestamp(System.currentTimeMillis());
        this.author = author;
        this.chatroom = chatroom;
    }
    public ImageMessage(String author, String chatroom, byte[] img, Timestamp timestamp){
        this.img = img.clone();
        this.timestamp = timestamp;
        this.author = author;
        this.chatroom = chatroom;
    }
    public String getAuthor(){
        return author;
    }
    public void setAuthor(String author){
        this.author = author;
    }

    public Timestamp getTimestamp(){
        return timestamp;
    }

    public byte[] getContent(){
        return img;
    }
    public void setContent(byte[] img){
        this.img = img.clone();
    }
    public String getChatroom(){
        return chatroom;
    }
    public void accept(Visitor visitor){
        visitor.visit(this);
    }
}
