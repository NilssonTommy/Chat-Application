package com.example;

import java.util.*;
public class MessageHandler {
    private PortalConnection pc;
    public MessageHandler(){
        pc = PortalConnection.getInstance();
    }
    public void addMessage(Message msg){
        if (msg instanceof TextMessage){
            TextMessage textmsg = (TextMessage)msg;
            pc.addMsg(textmsg.getAuthor(),textmsg.getContent(), textmsg.getTimestamp(), textmsg.getChatroom());
        } else if (msg instanceof ImageMessage){
            ImageMessage image = (ImageMessage)msg;
            //behöver en till metod för att lägga till imagemessages.
        }
        for(UserInterface user: getUsers(msg.getChatroom())){
            Broadcaster.getInstance().getObservable().notify(user.getUsername(), msg);
        }
    }
    private List<UserInterface> getUsers(String chatroom){
        return pc.UserList(chatroom);
    }
}
