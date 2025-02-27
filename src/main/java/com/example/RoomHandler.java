package com.example;

public class RoomHandler {
    private PortalConnection pc;

    public RoomHandler() {
        this.pc = PortalConnection.getInstance();
    }

    public ChatroomInterface clientRequest(ChatroomInterface model) {

        switch(model.getAction()){

            case CREATE:
            if(pc.createRoom(model.getUsername(),  model.getRoomName())){

                model.setStatus(true);
            }
            else {
                model.setStatus(false);
            }
            break;

            case JOIN:
            if(pc.addRoom(model.getUsername(), model.getRoomName())){

                model.setStatus(true);
            }
            else{
                model.setStatus(false);
            }
            break;

            case SELECT:
            model.setUsers(pc.UserList(model.getRoomName()));
            model.getChatLog().setHistory(pc.getChatLog(model.getRoomName()));
            
        }
        return model;
    }
}


