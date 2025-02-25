package com.example;

import java.io.Serializable;

public class UserResponse implements UserInterface, Serializable {

    private String username;
    private Boolean status;
    private UserAction action;
    

    public UserResponse(String name, Boolean status, UserAction action) {
        this.username = name;
        this.status = status;
        this.action = action;
        // private String firstName;
        // private String lastName;
        // private String birthDay;
        // private String city;
        // private String eMail;
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

}