package com.example;
import java.awt.event.*;

public class LoginController implements Observer {

    private LoginWindow loginWindow;
    private RegisterWindow registerWindow;
    private UserInterface user;
    private ClientNetwork clientNetwork;
    
    public LoginController() {
        this.loginWindow = new LoginWindow();
        loginWindow.addLoginListener(e ->loginHandler());
        registerUser();
        clientNetwork = ClientNetwork.getInstance();
        initObservable();
    }

    public void update(Object object) {
        user = (UserInterface)object;
        switch(user.getAction()) {
            
            case LOGIN:
                if (user.getStatus()) {
                    new ChatClientController(user);
                } else {
                    loginWindow.invalidUsername();
                }break;

            case REGISTER:
                if (user.getStatus()) {
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