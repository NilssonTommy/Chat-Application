package com.example.Server;

import java.util.ArrayList;

import com.example.Client.LoginWindow.UserRequest;
import com.example.Client.LoginWindow.UserResponse;
import com.example.Helpers.UserInterface;

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
            default:
            try {
                throw new Exception("UserAction is incorrect");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return userResponse;
    }
}