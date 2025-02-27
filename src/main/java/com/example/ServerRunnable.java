package com.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerRunnable implements Runnable {

    private Socket s;

    public ServerRunnable(Socket socket) {
        this.s = socket; 
    }

    @Override
    public void run() {
        
        try(
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream())
            )
            {
                
                Visitor visitor = new ClientHandler(oos);
                while(true) {
                    Object receivedObject = ois.readObject();

                    if(receivedObject instanceof Visitable) {
                       ( (Visitable) receivedObject).accept(visitor);
                    }
                }
            }catch (IOException | ClassNotFoundException e) {
                System.out.println("Client disconnected");
            }
        }
}