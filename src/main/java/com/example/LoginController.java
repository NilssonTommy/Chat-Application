package com.example;
import java.awt.event.*;

public class LoginController implements Observer {

    private LoginWindow loginWindow;
    private RegisterWindow registerWindow;
    private UserInterface user;
    private UserResponse userResponse;
    private ClientNetwork clientNetwork;
    
    public LoginController() {
        this.loginWindow = new LoginWindow();
        loginWindow.addLoginListener(e ->loginHandler());
        registerUser();
        clientNetwork = ClientNetwork.getInstance();
        initObservable();
    }

    public void update(Object object) {
        userResponse = (UserResponse)object;
        switch(userResponse.getAction()) {
            
            case LOGIN:
                if (userResponse.getStatus()) {
                    new ChatClientController(userResponse);
                } else {
                    loginWindow.invalidUsername();
                }break;

            case REGISTER:
                if (userResponse.getStatus()) {
                    registerWindow.registrationComplete();
                } else {
                    registerWindow.registrationFailed();
                } break;
        }
    }

    private void initObservable(){
        clientNetwork.getClientRunnable().getObservableMap().addSubscriber("login", this);
    }

    private void loginHandler() {
        user = new UserRequest(loginWindow.getUsername(), false, UserAction.LOGIN);
        clientNetwork.checkUsername(user);
    }

    private void registerUser(){
        loginWindow.addRegisterListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                registerWindow = new RegisterWindow();
                registerHandler();
            }
        });
    }

    private void registerHandler() {
        registerWindow.addRegistrationListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                user = new UserRequest(registerWindow.getUsername(), false, UserAction.REGISTER);
                clientNetwork.checkUsername(user);
            }
        });
    }
}