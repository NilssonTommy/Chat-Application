package com.example;
import java.util.List;

/**
 * ChatClientController hanterar användarens session efter inloggning.
 * Klassen ansvarar för att hantera chattrumsval, samt initiering av ChatClientModel och ChatClientGUI.
 */
public class ChatClientController implements Observer {
    
    private ChatClientModel model; // Referens till användarens data.
    private ChatClientGUI gui; // Användargränssnitt efter inloggning.
    private ClientNetwork clientNetwork; // Singleton-instansen av ClientNetwork.

    /**
     * Konstruktor som tar emot ett validerat användarnamn från LoginController.
     * Initierar ChatClientModel och ChatClientGUI.
     * Registrerar ChatClientController som en observer till ClientNetwork.
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
            ChatroomController chatRoomController = new ChatroomController(roomName);
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
        if (obj instanceof List<?>) { // Kontrollerar om objektet är en lista av något slag.
            List<?> rawList = (List<?>) obj; // Typecastar till en okänd typ av lista.
        
            // Checka om alla element är en sträng
            boolean allStrings = true;
            for (Object element : rawList) {
                if (!(element instanceof String)) {
                    allStrings = false;
                    break;
                }
            } 

            if (allStrings) {
                // "Undertryck" varningar eftersom vi hävdar att listan innehåller strängar
                @SuppressWarnings("unchecked")
                List<String> stringList = (List<String>) rawList;
    
                // Nu kan vi använda stringList om en List<String>
                System.out.println("List contains: " + stringList); // Debug
            } else {
                System.out.println("The list is not a List<String>"); // Debug
            }
        } else {
            System.out.println("Object is not a List at all."); // Debug
        }

        // gui.refresh(); Behöver fixas efter att GUI:t är implementerat
    }
}