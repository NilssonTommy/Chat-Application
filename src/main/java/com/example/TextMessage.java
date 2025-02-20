package com.example;
import java.sql.Timestamp;

public class TextMessage implements Message{
    private String author;
    private Timestamp timestamp;
    private String text;

    public TextMessage(String author ,String text){
        this.text = text;
        timestamp = new Timestamp(System.currentTimeMillis());
        this.author = author;
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
}
