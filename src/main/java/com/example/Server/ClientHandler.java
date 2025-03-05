
package com.example.Server;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.example.Helpers.*;
import com.example.Client.LoginWindow.UserRequest;
public class ClientHandler implements Visitor, Observer{

    private ObjectOutputStream oos;
    private LoginHandler loginHandler;
    private MessageHandler messageHandler;
    private RoomHandler roomHandler;

    public ClientHandler(ObjectOutputStream oos) {
        this.oos = oos;
        this.loginHandler = new LoginHandler();
        this.messageHandler = new MessageHandler();
        this.roomHandler = new RoomHandler();
    }

    @Override
    public void visit(UserRequest user) {

        UserInterface userResponse = loginHandler.checkUsername(user);
        writeOutputStream(userResponse);
    }

    public void visit(Message msg){
        messageHandler.addMessage(msg);
    }

    @Override
    public void visit(ChatroomInterface chatroomModel) {
        ChatroomInterface returnModel = roomHandler.clientRequest(chatroomModel);
        if(chatroomModel.getAction() == UserAction.SELECT){
            Broadcaster.getInstance().getObservable().addSubscriber(chatroomModel.getRoomName(), this);
        }
        writeOutputStream(returnModel);
    }

    public void update(Object obj){
        writeOutputStream(obj);
    }
    private synchronized void writeOutputStream(Object obj){
        try {
            oos.writeObject(obj);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  

}
