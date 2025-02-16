
/**
 * ChatClientController hanterar användarens session efter inloggning.
 * Klassen ansvarar för validering av användarnamn, hantering av chattrumsval,
 * samt initiering av ChatClientModel och ChatClientGUI.
 */
public class ChatClientController implements Observer {
    
    private ChatClientModel model; // Skapar en referens till ChatClientModel som lagrar avändarsessionens data.
    private ChatClientGUI gui; // Skapar en referens till ChatClientGUI.
    private ClientNetwork clientNetwork; // Skapar en referens till singelton-instansen ClientNetwork som kommunicerar med servern.

    /**
     * Konstruktor som hämtar en singleton-instans av ClientNetwork samt
     * lägger till klassen som en observer av ChatClientNetwork.
     */
    public ChatClientController(){
        this.clientNetwork=ClientNetwork.getInstance();
        clientNetwork.addObserver(this);
    }

    /**
     * Validerar användarnamnet via ClientNetwork.
     * Om användarnamnet är korrekt initieras ChatClientModel och ChatClientGUI.
     * @param username Användarnamnet som skall valideras.
     * @return true om användarnamnet är korrekt, annars false.
     */
    public boolean validateUsername(String username){
        boolean isValid = clientNetwork.checkUsername(username); // Validerar användarnamnet via ClientNetwork.
        if(isValid){
            model = new ChatClientModel(username); // Skapar en ny instans av ChatClientModel om användarnamner är korrekt.
            gui = new ChatClientGUI(model); // Skapar GUI.isValid
            System.out.println("Användarnamnet validerat: "+ username); // TEST
        } else{
            System.out.println("Felaktigt användarnamn: " + username); // TEST
        }
        return isValid;
    }

    /**
     * Hanterar användarens val av chattrum.
     * Om chattrummet finns i modellens lista över chattrum initieras en ChatRoomController.
     * @param roomName Namnet på det valda rummet.
     */
    public void onRoomSelected(String roomName){
        if(model.getChatRooms().contains(roomName)){
            ChatRoomController chatRoomController = new ChatRoomController(roomName, clientNetwork);
            System.out.println("Chattrum valt" + roomName); // Felsökning   
        } else {
            System.out.println("Fel, chattrum" + roomName + "existerar inte"); // Felsökning
        }
    }

    /**
     * Skapar ett nytt chattrum och lägger till det i modellen och servern.
     * @param roomName Namnet på det nya rummet.
     */
    public void createRoom(String roomName) {
        if (!model.getChatrooms().contains(roomName)) { // Kontrollera om rummet redan existerar i modellen.
            model.addChatroom(roomName); // Lägg till rummet i modellen.
            clientNetwork.createRoomOnServer(roomName); // Informera servern via ClientNetwork.
            System.out.println("Nytt chattrum skapat: " + roomName);
        } else {
            System.out.println("Ett chattrum med namnet \"" + roomName + "\" finns redan.");
        }
    }

    /**
     * Startar gränssnittet (GUI) för klienten.
     * GUI:t visar användarens tillgängliga chattrum och kopplar eventlyssnare för val av rum.
     */
    public void startGUI() {
        if (gui != null) {
            gui.show(); // Visa GUI:t
            gui.setRoomSelectionListener(roomName -> onRoomSelected(roomName)); // Koppla eventlyssnare
            System.out.println("ChatClientGUI startat."); // Felsökning
        } else {
            System.out.println("Fel: GUI:t är inte initierat."); // Felsökning
        }
    }

    /**
     * Hanterar notifieringar från observerade objekt (Observable).
     * Denna metod används för att uppdatera modellen och GUI:t baserat på notifieringar som skickas
     * från till exempel ClientNetwork.
     * 
     * För närvarande stöds notifieringar av typen String. Om notifieringen representerar
     * skapandet av ett nytt chattrum (med prefixet "NewRoom:"), läggs det nya rummet till i modellen
     * och GUI:t uppdateras om det är initierat.
     * 
     * @param obj Notifieringen som skickats av det observerade objektet. Förväntas vara av typen {@code String}.
     *            T.ex. "NewRoom:RoomName" för att signalera att ett nytt rum har skapats.
     */
    @Override
    public void update(Object obj) {
        if (obj instanceof String) { // 
            String notification = (String) obj;
            System.out.println("Notifiering mottagen: " + notification); // Felsökning
            if (notification.startsWith("NewRoom:")) {
                String newRoomName = notification.substring(8); // Extraherar namnet på det nya rummet. T.Ex. "NewRoom: Room1" -> "Room1"
                model.addChatroom(newRoomName); // Lägger till det nya rummet i ChatClientModel.
                if (gui != null) { // Uppdaterar gränssnittet om GUI:t är initierat.
                    gui.refresh();
                }
                System.out.println("Nytt rum tillagt i modellen och GUI: " + newRoomName); // Felsökning
            }
        }
    }
}
