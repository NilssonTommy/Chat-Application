package com.example;
import java.util.*;

public interface ChatClientInterface {

    List<String> getChatrooms();
    String getUsername();
    void setChatrooms(List<String> chatrooms);
    void addChatroom(String chatroom);
    
}