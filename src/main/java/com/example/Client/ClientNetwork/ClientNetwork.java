package com.example.Client.ClientNetwork;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.example.Helpers.ChatroomInterface;
import com.example.Helpers.Message;
import com.example.Helpers.UserInterface;

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