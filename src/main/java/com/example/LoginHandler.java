package com.example;

import java.util.ArrayList;

public class LoginHandler {

    private PortalConnection pc;

    public LoginHandler() {
        this.pc = PortalConnection.getInstance();
    }

    public UserInterface checkUsername(UserRequest user) {
        UserResponse userResponse = new UserResponse(user.getUsername(), false, user.getAction(), new ArrayList<String>());

        switch(user.getAction()){
            case LOGIN:
            if(pc.login(user.getUsername())) {
                userResponse.setStatus(true);
                userResponse.setRoomList(pc.getChatrooms(user.getUsername()));
            }else{
                userResponse.setStatus(false);
            }
                break;
            case REGISTER:
            if(pc.createUser(user.getUsername())) {
                userResponse.setStatus(true);
            }else{
                userResponse.setStatus(false);
            }
                break;
        }
        return userResponse;
    }
}