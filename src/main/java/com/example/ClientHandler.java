
package com.example;
import java.io.*;
import java.net.Socket;
public class ClientHandler implements Visitor, Observer{

    private ObjectOutputStream oos;
    private LoginHandler loginHandler;
    private MessageHandler messageHandler;
    private ChatroomHandler chatroomHandler;

    public ClientHandler(ObjectOutputStream oos) {
        this.oos = oos;
        this.loginHandler = new LoginHandler();
        this.messageHandler = new MessageHandler();
        this.chatroomHandler = new ChatroomHandler();
    }

    @Override
    public void visit(UserRequest user) {

        UserInterface userResponse = loginHandler.checkUsername(user);
        if(userResponse.getStatus()){
            Broadcaster.getInstance().getObservable().addSubscriber(userResponse.getUsername(), this);
        }
        try{
        oos.writeObject(userResponse);
        oos.flush();
        }catch(IOException e){
            System.err.println("Couldnt write Object");
        }
    }

    public void visit(Message msg){
        messageHandler.addMessage(msg);
    }

    public void visit(ChatroomInterface model){
        model = chatroomHandler.getChatroomModel(model);
        try {
            oos.writeObject(model);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void update(Object obj){
        try {
            oos.writeObject(obj);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  

}
