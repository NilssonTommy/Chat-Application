package com.example;

public class ChatroomHandler {
    private PortalConnection pc;
    public ChatroomHandler(){
        pc = PortalConnection.getInstance();
    }

    public ChatroomInterface getChatroomModel(ChatroomInterface model){
        model.setUsers(pc.UserList(model.getRoomName()));
        ChatHistoryInterface chatlog = model.getChatLog().setHistory(pc.getChatLog(model.getRoomName()))
        model.setChatLog(chatlog);
        return model;
    }
}
