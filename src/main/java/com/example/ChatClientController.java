package com.example;

/**
 * ChatClientController hanterar användarens session efter inloggning.
 * Klassen ansvarar för att hantera chattrumsval, samt initiering av ChatClientModel och ChatClientGUI.
 */
public class ChatClientController implements Observer {
    
    private ChatClientModel model; // Referens till användarens data.
    private ChatClientGUI gui; // Referens till GUI som visar användarens gränssnitt efter inloggning.
    private ClientNetwork clientNetwork; // Singleton-instansen av ClientNetwork.

    /**
     * Konstruktor som tar emot ett validerat användarnamn från LoginController.
     * Skapar ChatClientModel och ChatClientGUI.
     * @param username Användarnamnet för den inloggade användaren.
     */
    public ChatClientController(String username) {
        this.clientNetwork = ClientNetwork.getInstance();
        clientNetwork.addObserver(this);
        this.model = new ChatClientModel(username);
        this.gui = new ChatClientGUI(model);
        System.out.println("ChatClientController skapad för användare: " + username);
    }

    /**
     * Hanterar användarens val av chattrum.
     * Om chattrummet finns i modellens lista över chattrum initieras en ChatRoomController.
     * @param roomName Namnet på det valda rummet.
     */
    public void onRoomSelected(String roomName) {
        if (model.getChatrooms().contains(roomName)) {
            ChatRoomController chatRoomController = new ChatRoomController(roomName, clientNetwork);
            System.out.println("Chattrum valt: " + roomName); 
        } else {
            System.out.println("Fel: Chattrum " + roomName + " existerar inte");
        }
    }

    /**
     * Skapar ett nytt chattrum och lägger till det i modellen och skickar till servern.
     * @param roomName Namnet på det nya rummet.
     */
    public void createRoom(String roomName) {
        if (!model.getChatrooms().contains(roomName)) { // Kontrollerar om rummet redan existerar i modellen.
            model.addChatroom(roomName); // Lägger till rummet i modellen.
            
            // Om ClientNetwork har en metod för att informera servern, använd den
            if (clientNetwork != null) {
                System.out.println("Meddelar servern om att skapa rummet: " + roomName);
                // Här kan vi anropa en faktisk metod i ClientNetwork för att skapa rummet på servern.
                // clientNetwork.createRoomOnServer(roomName); // Avkommentera när metoden existerar.
            }

            System.out.println("Nytt chattrum skapat: " + roomName);
        } else {
            System.out.println("Ett chattrum med namnet \"" + roomName + "\" finns redan.");
        }
    }

    /**
     * Startar gränssnittet (GUI) för klienten.
     */
    public void startGUI() {
        if (gui != null) {
            gui.show();
            gui.setRoomSelectionListener(roomName -> onRoomSelected(roomName));
            System.out.println("ChatClientGUI startat.");
        } else {
            System.out.println("Fel: GUI:t är inte initierat.");
        }
    }

    /**
     * Hanterar notifieringar från observerade objekt (Observable).
     * @param obj Notifieringen från det observerade objektet.
     */
    @Override
    public void update(Object obj) {
        if (obj instanceof String) {
            String notification = (String) obj;
            System.out.println("Notifiering mottagen: " + notification);
            if (notification.startsWith("NewRoom:")) {
                String newRoomName = notification.substring(8);
                model.addChatroom(newRoomName);
                if (gui != null) {
                    gui.refresh();
                }
                System.out.println("Nytt rum tillagt i modellen och GUI: " + newRoomName);
            }
        }
    }
}