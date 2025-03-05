package com.example.Client.ChatroomWindow;

import java.util.*;

import com.example.Helpers.ChatHistoryInterface;
import com.example.Helpers.Message;

public class ChatHistory implements ChatHistoryInterface {
    private String roomname;
    private List<Message> chatlog;
    public ChatHistory(String roomname){
        this.roomname = roomname;
        chatlog = new LinkedList<>();
    }
    public List<Message> getHistory(){
        return chatlog;
    }
    public void setHistory(List<Message> chatlog){
        this.chatlog = new LinkedList<Message>(chatlog);
    }
    public void addMessage(Message msg){
        chatlog.add(msg);
    }
    public String getRoomname(){
        return roomname;
    }
}
