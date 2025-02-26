package com.example;

import java.util.*;


// TODO: Implement Visitable

/**
 * ChatRoomModel manages the data for a specific chatroom.
 * It handles messages, the user list, and communicates with ClientNetwork.
 * Implements Observable to notify ChatRoomController and GUI about updates.
 */
public class ChatroomModel implements ChatroomInterface{
    private String roomName; // Name of the chatroom.
    private ChatHistoryInterface chathistory;
    private List<UserInterface> users; // List of users in the chatroom.
    private Observable obschat, obsuser;

    /**
     * Constructor initializes the chatroom model.
     * @param roomName The name of the chatroom.
     */
    public ChatroomModel(String roomName) {
        this.roomName = roomName;
        this.chathistory = new ChatHistory(roomName);
        this.users = new LinkedList<>();
        this.obschat = new Observable();
        this.obsuser = new Observable();
    }

    /**
     * 
     */
    @Override
    public void update(Object obj){
        if(obj instanceof ChatroomModel){
            ChatroomModel model = (ChatroomModel)obj;
            this.chathistory = model.getChatLog();
            this.users = new LinkedList<UserInterface>(model.getUsers());
            obschat.notify(chathistory);
            obsuser.notify(users);
        } else if (obj instanceof Message) {
            chathistory.addMessage((Message)obj);
            obschat.notify((Message)obj);
        } else if (obj instanceof User) {
            users.add((UserInterface)obj);
            obsuser.notify((User)obj);
        }
    }

    /**
     * Returns the current list of users in the chatroom.
     * @return List of users.
     */
    public List<UserInterface> getUsers() {
        return users;
    }

    /**
     * Returns the chathistory of the chatroom.
     */
    public ChatHistoryInterface getChatLog() {
        return chathistory;
    }
    /**
     * Returns the name of the chatroom
     * @return name of the chatroom
     */
    public String getRoomName(){
        return roomName;
    }

    public void setChatLog(ChatHistoryInterface chathistory){
        this.chathistory = chathistory;
    }

    public void setUsers(List<UserInterface> users){
        this.users = new LinkedList<UserInterface>(users);
    }


    /**
     * Set observers.
     * @param chat
     * @param user
     */
    public void setObservers(Observer chat, Observer user){
        obschat.addSubscriber(chat);
        obsuser.addSubscriber(user);
    }

    /**
     * 
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
    
}
