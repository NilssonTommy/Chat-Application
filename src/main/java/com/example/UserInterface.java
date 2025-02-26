package com.example;

import java.util.List;

public interface UserInterface {

    public void setUsername(String name);

    public String getUsername();

    public void setStatus(Boolean status);

    public Boolean getStatus();

    public UserAction getAction();

    public void setAction(UserAction action);

}