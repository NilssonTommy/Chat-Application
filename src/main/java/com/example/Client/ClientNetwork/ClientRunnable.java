package com.example.Client.ClientNetwork;
import java.io.ObjectInputStream;

import com.example.Client.ChatroomWindow.ChatroomModel;
import com.example.Helpers.*;

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
                if(obj instanceof UserInterface){
                    oMap.notify("login", obj);

                } else if (obj instanceof Message){
                    oMap.notify(((Message)obj).getChatroom(), obj);

                } else if (obj instanceof ChatroomInterface){
                    ChatroomInterface model = ((ChatroomModel)obj);

                    if( model.getAction() == UserAction.SELECT){
                        oMap.notify(model.getRoomName(), obj);

                    } else if (((ChatroomModel)obj).getAction() == UserAction.NOTIFY){                        
                        oMap.notify(model.getRoomName(), model.getUser());

                    } else{
                        oMap.notify("chatClientController", obj);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableMap getObservableMap() {
        return oMap;
    }
}