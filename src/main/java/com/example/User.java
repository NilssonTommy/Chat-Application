package com.example;

import java.io.Serializable;

public class User implements UserInterface, Serializable {

    String username;
    Boolean status;

    public User(String name, Boolean status) {
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
}