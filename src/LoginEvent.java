import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class LoginEvent {

    private LoginWindow login;
    private ChatClientController controller;

    public LoginEvent() {
        this.login = new LoginWindow();
        login.addLoginListener(e -> loginHandler());
    }

    private void loginHandler(){
        if(ClientNetwork.checkUsername(login.getUsername())){
            controller = new ChatClientController();
        }
        else
            login.invalidUsername();

    }
}