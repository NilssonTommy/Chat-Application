import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class LoginEvent {

    private LoginWindow login;
    
    public LoginEvent() {
        this.login = new LoginWindow();
        login.addLoginListener(e ->loginHandler());
    }

    private void loginHandler(){
        ClientNetwork clientNetwork = ClientNetwork.getInstance();
        if(clientNetwork.checkUsername(login.getUsername())){
         new ChatClientController();
        }
        else
            login.invalidUsername();

    }
}