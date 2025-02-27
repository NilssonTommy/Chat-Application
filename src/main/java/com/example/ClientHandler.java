
package com.example;
import java.io.IOException;
import java.io.ObjectOutputStream;
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

    @Override
    public void visit(ChatroomInterface chatroomModel) {
        ChatroomInterface returnModel = roomHandler.clientRequest(chatroomModel);
        if(chatroomModel.getAction() == UserAction.SELECT){
            Broadcaster.getInstance().getObservable().addSubscriber(chatroomModel.getRoomName(), this);
        }
        try{
        oos.writeObject(returnModel);
        oos.flush();
        }catch(IOException e){
            System.err.println("Couldnt write Object");
        }
    }

    public void update(Object obj){
        try {
            System.out.println("Broadcasting for chatroom");
            oos.writeObject(obj);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  

}
