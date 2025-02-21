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
                if(obj instanceof User) {
                        oMap.notify("login", obj);
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