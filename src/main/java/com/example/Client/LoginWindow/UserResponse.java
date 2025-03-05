package com.example.Client.LoginWindow;

import java.io.Serializable;
import java.util.*;

import com.example.Helpers.*;

public class UserResponse implements UserInterface, Serializable {

    private String username;
    private Boolean status;
    private UserAction action;
    private List<String> roomList;
    

    public UserResponse(String name, Boolean status, UserAction action, List<String> roomList) {
        this.username = name;
        this.status = status;
        this.action = action;
        this.roomList = new ArrayList<String> (roomList);
    }

    public UserResponse(String name, Boolean status, UserAction action) {
        this.username = name;
        this.status = status;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getUsername() {
        return username;
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

    public UserAction getAction(){
        return action;
    }

    public List<String> getRoomList(){
        return roomList;
    }

    public void setRoomList(List<String> roomList){
        this.roomList = new ArrayList<String> (roomList);
    }

}