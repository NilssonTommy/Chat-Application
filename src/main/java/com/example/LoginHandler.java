package com.example;

public class LoginHandler {

    private PortalConnection pc;

    public LoginHandler() {
        this.pc = PortalConnection.getInstance();;
    }

    /* Verify username */
    public UserInterface checkUsername(UserInterface user) {
        switch(user.getAction()){
            case LOGIN:
            if(pc.login(user.getUsername())) {
                user.setStatus(true);
            }else{
                user.setStatus(false);
            }
                break;
            case REGISTER:
            if(pc.createUser(user.getUsername())) {
                user.setStatus(true);
            }else{
                user.setStatus(false);
            }
                break;
        }
        return user;
    }
}
