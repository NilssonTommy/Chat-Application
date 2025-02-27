package com.example;

public class RoomHandler {
    private PortalConnection pc;

    public RoomHandler() {
        this.pc = PortalConnection.getInstance();
    }

    /* Verify RoomID */
    public ChatroomInterface clientRequest(ChatroomInterface model) {

        switch(model.getAction()){

            case CREATE: //Skapar ett rum
            if(pc.createRoom(model.getUsername(),  model.getRoomName())){

                model.setStatus(true);
            }
            else {
                model.setStatus(false);
            }
            break;

            case JOIN: //Lägger till rum i ChatClient listan
            if(pc.addRoom(model.getUsername(), model.getRoomName())){

                model.setStatus(true);
            }
            else{
                model.setStatus(false);
            }
            break;

            case SELECT: //Open a chatroom
            //model.setUserlist(pc.getUserList());
            //model.updateChatLog(pc.getChatLog(model.getRoomName()));
        }
        return model;
    }
}


