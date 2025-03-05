package com.example.Client.ChatClientWindow;

import java.util.List;

import com.example.Client.LoginWindow.UserResponse;
import com.example.Helpers.ChatClientInterface;
import com.example.Helpers.UserInterface;

public class ChatClientModel implements ChatClientInterface {
    private UserInterface user;
    private List<String> chatrooms;

    public ChatClientModel(UserInterface user){
        this.user = user;
        chatrooms = ((UserResponse)user).getRoomList();
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
