package com.example;
import java.io.*;

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
                }
                else if(obj instanceof ChatroomModel) {
                    oMap.notify("room", obj);
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