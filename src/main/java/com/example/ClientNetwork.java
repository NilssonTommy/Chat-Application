package com.example;
import java.io.IOException;   // Imports I/O Streams classes
import java.io.ObjectInputStream;  // Imports the socket class
import java.io.ObjectOutputStream;
import java.net.Socket;

public final class ClientNetwork {

    private static ClientNetwork instance;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private ClientRunnable cR;

    private ClientNetwork() {
        try{
            System.out.println("Client started");
            Socket s = new Socket("localhost",6666);
                    
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.flush();

            ois = new ObjectInputStream(s.getInputStream());

            cR = new ClientRunnable(ois);

            new Thread(cR).start();

        } catch (Exception e) {System.err.println(e);}

        System.err.println("startad");
    }

    public void checkUsername(UserInterface user) {
        try {
            oos.writeObject(user);
            oos.flush();
        }catch (Exception e) {System.err.println(e);}
    }

     /* Metod för att välja rum. Vi skriver och flushar */
     public void checkRoom(ChatroomInterface chatroomModel) {

        System.out.println("Vi e inne i checkroom ClientNetwork: " + chatroomModel.getRoomName());

        try{
            oos.writeObject(chatroomModel);
            oos.flush();
        }catch (Exception e) {System.err.println(e);}
    }
     

    public static ClientNetwork getInstance() {
        if (instance == null){
            instance = new ClientNetwork();
        }
        return instance;
    }

    public ClientRunnable getClientRunnable() {
        return cR;
    }

    /**
     * Metod som skickar meddelande från ChatroomController vidare till servern.
     * @param msg Meddelandet som användaren skickat.
     */
    public void sendMessage(Message msg){
        try {
            oos.writeObject(msg);
            oos.flush();
        }catch (Exception e) {
            System.err.println(e);}
        }

    public void requestRoomData(ChatroomInterface model){
        try {
            oos.writeObject(model);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}