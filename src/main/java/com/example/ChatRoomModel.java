package com.example;

public class ChatRoomModel {
    public ChatRoomModel(){
        ClientNetwork client = ClientNetwork.getInstance();
    }

    public static void getChatLog() {
        client.getChatLog();
    }

    public static void sendMsg(){
        client.sendMsg();
    }

    public static void updateChatLog(){
        client.updateChatLog();
    }





    //Metod för hämta och uppdatera chathistorik, alla clients i listan, ska vara en observer(observera alla uppdateringar)
}
