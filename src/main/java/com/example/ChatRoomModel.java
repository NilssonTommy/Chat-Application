package com.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.basic.BasicComboBoxUI;

/**
 * ChatRoomModel manages the data for a specific chatroom.
 * It handles messages, the user list, and communicates with ClientNetwork.
 * Implements Observable to notify ChatRoomController and GUI about updates.
 */
public class ChatRoomModel implements Observer, ChatroomInterface{
    private String roomName; // Name of the chatroom.
    private ChatHistoryInterface chathistory;
    private List<User> users; // List of users in the chatroom.
    private ClientNetwork clientNetwork; // Handles communication with the server, ClientNetwork is a singelton.

    private Observable obschat, obsuser;

    /**
     * Constructor initializes the chatroom model.
     * @param roomName The name of the chatroom.
     */
    public ChatRoomModel(String roomName) {
        this.roomName = roomName;
        this.chathistory = new ChatHistory();
        this.users = new ArrayList<>();
        this.clientNetwork = ClientNetwork.getInstance();

        this.obschat = new Observable();
        this.obsuser = new Observable();
    }

    @Override
    public void update(Object obj){
        if(obj instanceof ChatRoomModel){
            ChatRoomModel model = (ChatRoomModel)obj;
            this.chathistory = model.getChatlog();
            this.users = model.getUsers();
            obschat.notify(chathistory);
            obsuser.notify(users);
        } else if (obj instanceof Message) {
            obschat.notify((Message)obj);
        } else if (obj instanceof User) {
            obsuser.notify((User)obj);
        }
    }

    /**
     * Returns the current list of users in the chatroom.
     * @return List of users.
     */
    public List<User> getUsers() {
        return users;
    }

    public ChatHistoryInterface getChatLog() {
        return chathistory;
    }

    public void setObservers(Observer chat, Observer user){
        obschat.addSubscriber(chat);
        obsuser.addSubscriber(user);
    }
}
