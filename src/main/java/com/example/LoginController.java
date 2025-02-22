package com.example;
public class LoginController implements Observer {

    private LoginWindow loginWindow;
    private User user;
    private ClientNetwork clientNetwork;
    
    public LoginController() {
        this.loginWindow = new LoginWindow();
        loginWindow.addLoginListener(e ->loginHandler());
        this.clientNetwork = ClientNetwork.getInstance();
        initObservable();
    }

    public void update(Object object) {
        user = (User)object;
        if(user.getStatus()) {
            //new ChatClientController(user);
            System.out.println("ChatClientController skapas här");
        } else 
            loginWindow.invalidUsername();
    }

    private void initObservable(){
        clientNetwork.getClientRunnable().getObservableMap().addSubscriber("login", this);
    }

    private void loginHandler(){
        user = new User(loginWindow.getUsername(), false);
        clientNetwork.checkUsername(user);
    }
}