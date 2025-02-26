
package com.example;
import java.io.IOException;
import java.io.ObjectOutputStream;
public class ClientHandler implements Visitor{

    private ObjectOutputStream oos;
    private LoginHandler loginHandler;
    private RoomHandler roomHandler;

    public ClientHandler(ObjectOutputStream oos) {
        this.oos = oos;
        this.loginHandler = new LoginHandler();
        this.roomHandler = new RoomHandler();
    }

    @Override
    public void visit(UserRequest user) {
        UserInterface userResponse = loginHandler.checkUsername(user);
       
        try{
        oos.writeObject(userResponse);
        oos.flush();
        }catch(IOException e) {
            System.err.println("Couldnt write Object");
        }
    }

    @Override
    public void visit(ChatroomModel chatroomModel) {
        ChatroomModel returnModel = roomHandler.clientRequest(chatroomModel);
        try{
        oos.writeObject(returnModel);
        oos.flush();
        }catch(IOException e){
            System.err.println("Couldnt write Object");
        }
    }
}
