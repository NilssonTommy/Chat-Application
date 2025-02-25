
package com.example;
import java.io.*;
public class ClientHandler implements Visitor{

    private ObjectOutputStream oos;
    private LoginHandler loginHandler;

    public ClientHandler(ObjectOutputStream oos) {
        this.oos = oos;
        this.loginHandler = new LoginHandler();
    }

    @Override
    public void visit(UserRequest user) {

        UserInterface userResponse = loginHandler.checkUsername(user);
        try{
        oos.writeObject(userResponse);
        oos.flush();
        }catch(IOException e){
            System.err.println("Couldnt write Object");
        }
    }

  

}
