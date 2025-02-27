package com.example;

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
            pc.addImgMsg(image.getAuthor(), image.getContent(), image.getTimestamp(), image.getChatroom());
        }
        System.out.println(msg.getChatroom());
        Broadcaster.getInstance().getObservable().notify(msg.getChatroom(), msg);
    }
}
