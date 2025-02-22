package com.example;

import java.io.*;
import java.net.*;

/* Receives input from client and sends ... */
public class ServerRunnable implements Runnable {

    private Socket s;
    private ClientHandler cH;

    public ServerRunnable(Socket socket, ClientHandler cHandler) {
        this.s = socket;
        this.cH = cHandler;
    }

    @Override
    public void run() {
        try (
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream())
            ){
            while(true) {
                Object receivedObject = ois.readObject();

                if(receivedObject instanceof User) {
                    User user = (User)receivedObject;
                    if(cH.checkUsername(user)){
                        user.setStatus(true);
                    }else{
                        user.setStatus(false);
                    }
                    oos.writeObject(user);
                    oos.flush();
                }
            }
        }catch (IOException | ClassNotFoundException e) {
            System.out.println("Client disconnected");
        }
    }
    

    /*{
        try (DataInputStream dis = new DataInputStream(s.getInputStream())) {
            while (true) {
                String str = (String) dis.readUTF();
                System.out.println("message= " + str);
            }
        } catch (IOException e) {
            System.out.println("Client disconnected");
        }
    }*/

}