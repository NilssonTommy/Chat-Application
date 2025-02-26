package com.example;

import java.io.*;
import java.net.*;

/* Receives input from client and sends ... */
public class ServerRunnable implements Runnable {

    private Socket s;

    public ServerRunnable(Socket socket) {
        this.s = socket;
    }

    @Override
    public void run() {
        

        try (
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream())
            ){
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