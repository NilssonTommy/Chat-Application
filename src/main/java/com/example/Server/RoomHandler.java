package com.example.Server;

import com.example.Client.ChatroomWindow.ChatroomModel;
import com.example.Helpers.*;

public class RoomHandler {
    private PortalConnection pc;

    public RoomHandler() {
        this.pc = PortalConnection.getInstance();
    }

    public ChatroomInterface clientRequest(ChatroomInterface model) {

        switch(model.getAction()){

            case CREATE:
            if(pc.createRoom(model.getUser().getUsername(),  model.getRoomName())){

                model.setStatus(true);
            }
            else {
                model.setStatus(false);
            }
            break;

            case JOIN:
            if(pc.addRoom(model.getUser().getUsername(), model.getRoomName())){
                Broadcaster.getInstance().getObservable().notify(model.getRoomName(), new ChatroomModel(model.getUser(), model.getRoomName(), UserAction.NOTIFY));
                model.setStatus(true);
            }
            else{
                model.setStatus(false);
            }
            break;

            case SELECT:
            model.setUsers(pc.UserList(model.getRoomName()));
            model.getChatLog().setHistory(pc.getChatLog(model.getRoomName()));
            break;

            default:
            try {
                throw new Exception("UserAction is incorrect");
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        return model;
    }
}


