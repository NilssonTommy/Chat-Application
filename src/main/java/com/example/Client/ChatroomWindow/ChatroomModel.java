package com.example.Client.ChatroomWindow;
import com.example.Helpers.*;
import com.example.Helpers.Observable;
import com.example.Helpers.Observer;

import java.util.*;



/**
 * ChatRoomModel manages the data for a specific chatroom.
 * It handles messages, the user list, and communicates with ClientNetwork.
 * Implements Observable to notify ChatRoomController and GUI about updates.
 */
public class ChatroomModel implements ChatroomInterface{
    private String roomName;
    private ChatHistoryInterface chathistory;
    private List<UserInterface> users;
    private Observable obschat, obsuser;
    private UserAction action;
    private Boolean status;
    private UserInterface user;




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

    public ChatroomModel(UserInterface user, String roomName, UserAction action){
        this.user = user;
        this.roomName = roomName;
        this.status = false;
        this.action = action;
        this.chathistory = new ChatHistory(roomName);
        this.obschat = new Observable();
        this.obsuser = new Observable();
        this.users = new LinkedList<>();
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
        } else if (obj instanceof UserInterface) {
            users.add((UserInterface)obj);
            obsuser.notify((UserInterface)obj);
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

    public void setStatus(Boolean status) {
        this.status = status;
    }
    public Boolean getStatus(){
        return status;
    }

    public void setAction(UserAction action) {
        this.action = action;
    }

    public UserAction getAction() {
        return action;
    }

    public UserInterface getUser() {
        return user;
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
