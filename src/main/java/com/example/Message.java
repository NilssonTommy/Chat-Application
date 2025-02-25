package com.example;
import java.io.Serializable;
import java.sql.Timestamp;
public interface Message extends Serializable{
    public String getAuthor();
    public Timestamp getTimestamp();
}
