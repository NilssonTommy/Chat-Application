package com.example;
import java.io.ObjectInputStream;

public class ClientRunnable implements Runnable {
      
    private ObjectInputStream ois;
    private ObservableMap oMap;

    public ClientRunnable(ObjectInputStream ois) {
        this.ois = ois;
        oMap = new ObservableMap();
    } 
    
    @Override
    public void run() {
        try {
            while (true) {
                Object obj = ois.readObject();
                if(obj instanceof UserInterface) {
                    oMap.notify("login", obj);
                } else if (obj instanceof Message){
                    System.out.println(((Message)obj).getChatroom() + ": Meddelande mottaget");
                    oMap.notify(((Message)obj).getChatroom(), obj);
                } else if (obj instanceof ChatroomInterface){

                    if( ((ChatroomModel)obj).getAction() == UserAction.SELECT){
                    oMap.notify(((ChatroomInterface)obj).getRoomName(), obj);
                    } else {
                        oMap.notify("chatClientController", obj);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Fel: " + e);
        }
    }

    public ObservableMap getObservableMap() {
        return oMap;
    }
}