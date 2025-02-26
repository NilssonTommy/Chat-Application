package com.example;
import java.io.Serializable;
import java.util.List;

public class ChatroomModel implements Visitable, Serializable {

    private String username;
    private String roomName;
    private Boolean status;
    private List<String> userList;
    private List<String> chatHistory;
    private UserAction action;

    public ChatroomModel(){
        ClientNetwork client = ClientNetwork.getInstance();
    }
  
    public ChatroomModel(String username, String roomName, UserAction action){
        this.username = username;
        this.roomName = roomName;
        this.status = false;
        this.action = action;
    }

    @Override
    public void accept(Visitor visitor) {
            visitor.visit(this);
    }

    public List<String> getChatLog() {
        return chatHistory;
    }

    public static void sendMsg(){
//        client.sendMsg();
    }

    public void updateChatLog(List<String> chatHistory) {
        for(String msg : chatHistory) {
            this.chatHistory.add(msg);
        }
    }

    public String getRoomName() {
        return roomName;
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

    public List<String> getUserList() {
        return userList;
    }

    public List<String> getChatHistory() {
        return chatHistory;
    }

    public String getUsername() {
        return username;
    }

}
