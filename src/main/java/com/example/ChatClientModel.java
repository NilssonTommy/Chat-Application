package com.example;
import java.util.*;

public class ChatClientModel implements ChatClientInterface {
    private UserInterface user;
    private List<String> chatrooms;

    public ChatClientModel(UserResponse user){
        this.user = user;
        chatrooms = user.getRoomList();
    }

    public String getUsername(){
        return user.getUsername();
    }

    public List<String> getChatrooms(){
        return chatrooms;
    }

    public void setChatrooms(List<String> chatrooms){
        if (this.chatrooms.size() == 0){
            this.chatrooms = chatrooms;
        } else {
            this.chatrooms.addAll(chatrooms);
        }
    }

    public void addChatroom(String chatroom){
        chatrooms.add(chatroom);
    }
}
