package com.example;
public final class ClientNetwork {
    private static ClientNetwork instance;

    private ClientNetwork() {

    }

    Boolean checkUsername(String Username){
       return "tommy".equals(Username);
    };

    public static ClientNetwork getInstance() {
        if (instance == null){
            instance = new ClientNetwork();
        }
        return instance;
    }

    /**
     * Metod som skickar meddelandet från ChatroomController vidare till servern.
     * @param messageText Meddelandet som användaren skickat.
     */
    public void sendMessage(String messageText){
        // Skall implementeras
    }
}
