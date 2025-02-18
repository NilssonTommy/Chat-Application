package com.example;
import java.util.*;
public interface ChatHistoryInterface{
    public void addMessage(Message msg);
    public List<Message> getHistory();
}