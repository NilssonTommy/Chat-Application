public class ChatRoomController {
    

    private ChatRoom chatRoom; // Referens till ChatRoom.
    private ChatRoomGUI gui; // Referens till ChatRoomGUI som är interfacet för varje chatrum.
    private ClientNetwork clientNetwork; // Singleton-instansen av ClientNetwork.



    // Knstruktor
    public ChatRoomController(String roomName, ClientNetwork clientNetwork){
        this.clientNetwork = ClientNetwork.getInstance();
        clientNetwork.addObserver(this);
    }

}
