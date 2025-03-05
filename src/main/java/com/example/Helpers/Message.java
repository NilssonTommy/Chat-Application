package com.example.Helpers;
import java.io.Serializable;
import java.sql.Timestamp;;
public interface Message extends Serializable, Visitable{
    public String getAuthor();
    public Timestamp getTimestamp();
    public String getChatroom();
}
