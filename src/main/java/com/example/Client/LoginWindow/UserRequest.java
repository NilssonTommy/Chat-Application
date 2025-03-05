package com.example.Client.LoginWindow;

import java.io.Serializable;

import com.example.Helpers.*;

public class UserRequest implements UserInterface, Serializable, Visitable {

    private String username;
    private Boolean status;
    private UserAction action;

    public UserRequest(String name, Boolean status, UserAction action) {
        this.username = name;
        this.status = status;
        this.action = action;
    }

    @Override
    public void setUsername(String name) {
        this.username = name;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public Boolean getStatus(){
        return status;
    }

    @Override
    public void setAction(UserAction action) {
        this.action = action;
    }

    @Override
    public UserAction getAction(){
        return action;
    }

    @Override
    public void accept(Visitor visitor) {
            visitor.visit(this);
    }
    
}
