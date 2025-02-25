package com.example;
import java.io.ObjectInputStream;   // Imports I/O Streams classes
import java.io.ObjectOutputStream;  // Imports the socket class
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
    }

    public void checkUsername(User user) {
        try {
            oos.writeObject(user);
            oos.flush();
        }catch (Exception e) {System.err.println(e);}
    }

    /* Create user logik */
    //      Uppdatera databas med en ny user

    /* Create room logik */
    //      Uppdatera databas när ett rum skapas
    //      Kommunicera med databasen när vi skapar ett nytt rum

    /* Join room logik */
    //      Retunera historik
    //      Retunera vilka användare som tillhör ett rum
    //      Uppdatera listan i databasen när någon ansluter sig til rummet för första gången

    /* Ska man kunna exa ett rum? */
    //      Logik för det

    /* Uppdateringar i realtd */
    //      Uppdatera databasen när vi skriver något.
    //      Läsa av databas historik i realtid.
    //      Läsa användarlistan kontinuerligt i realtid (när någon joinar).


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
     * Metod som skickar meddelandet från ChatroomController vidare till servern.
     * @param messageText Meddelandet som användaren skickat.
     */
    public void sendMessage(Message msg){
        // Skall implementeras
    }

}