package com.example;
import java.sql.Timestamp;

public class TextMessage implements Message{
    private String author;
    private Timestamp timestamp;
    private String text;
    private String chatroom;

    public TextMessage(String author, String chatroom, String text){
        this.text = text;
        timestamp = new Timestamp(System.currentTimeMillis());
        this.author = author;
        this.chatroom = chatroom;
    }
    public TextMessage(String author, String chatroom, String text, Timestamp timestamp){
        this.text = text;
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

    public String getContent(){
        return text;
    }
    public void setContent(String text){
        this.text = text;
    }
    public String getChatroom(){
        return chatroom;
    }
    public void accept(Visitor visitor){
        visitor.visit(this);
    }
}
