package com.example;
import java.util.List;

/**
 * ChatClientController handles the user's session after login.
 * The class is responsible for handling chatroom selection, 
 * as well as initializing ChatClientModel and ChatClientGUI.
 * This class is an observer of ClientNetwork (server communication).
 */
public class ChatClientController implements Observer {
    
    private ChatClientModel chatClientModel; // Stores user-related data.
    private ChatClientGUI chatClientGUI; // User interface after login.
    private ClientNetwork clientNetwork; // The singleton instance of ClientNetwork.
 
    /**
     * Constructor that receives a validated username from the LoginController.
     * Initializes ChatClientModel and ChatClientGUI.
     * Registers ChatClientController as an observer of ClientNetwork.
     * @param username The validated username of the logged in user.
     */
    public ChatClientController(String username) {
        this.clientNetwork = ClientNetwork.getInstance(); // Get the singleton instance of ClientNetwork.
        clientNetwork.addObserver(this); // Register this controller as an observer of ClientNetwork.
        this.chatClientModel = new ChatClientModel(username);
        this.chatClientGUI = new ChatClientGUI(chatClientModel);
        System.out.println("ChatClientController skapad för användare: " + username); // Debug.
    }

    /**
     * Manages the user's choice of chatroom.
     * If the chatroom is in the model's list of chatrooms, 
     * a ChatRoomController is initialized.
     * @param roomName The chatroom name of the selected room.
     */
    public void onRoomSelected(String roomName) {
        if (chatClientModel.getChatrooms().contains(roomName)) {
            System.out.println("Joining chatroom: " + roomName); // Debug
            new ChatroomController(roomName); // Initialize ChatroomController
        } else {
            System.out.println("Error: Chatroom '" + roomName + "' does not exist."); // Debug
        }
    }
    
    /**
     * Creates a new chatroom and adds it to the model and send it to the server.
     * @param roomName The chatroom name of the new room.
     */
    public void createRoom(String roomName) {
        if (!chatClientModel.getChatrooms().contains(roomName)) { 
            chatClientModel.addChatroom(roomName); // Add the new chatroom locally.

            // Send request to server (assumes clientNetwork.createRoom() exists)...
            if (clientNetwork != null) {
                System.out.println("Notifying server to create room: " + roomName); // Debug
                clientNetwork.createRoom(roomName); // TODO: Implement this in ClientNetwork.
            }
            System.out.println("New chatroom created: " + roomName); // Debug
        } else {
            System.out.println("Chatroom '" + roomName + "' already exists."); // Debug
        }
    }
    
    /**
     * Handles updates from observed objects (Observable).
     * @param obj The update notification from the observed object.
     */
    @Override
    public void update(Object obj) {
        if (obj instanceof List<?>) { // Check if object is a list.
            List<?> rawList = (List<?>) obj;

            // Ensure all elements are strings
            boolean allStrings = rawList.stream().allMatch(e -> e instanceof String);
            
            if (allStrings) {
                @SuppressWarnings("unchecked")
                List<String> chatrooms = (List<String>) rawList;
                
                chatClientModel.setChatrooms(chatrooms); // Update model

                System.out.println("Updated chatrooms: " + chatrooms); // Debug

                // Refresh the GUI once implemented
                if (chatClientGUI != null) {
                    // chatClientGUI.refresh(); // Uncomment once implemented
                }
            } else {
                System.out.println("Received a list, but not of type List<String>"); // Debug
            }
        } else {
            System.out.println("Received an update that is not a list"); // Debug
        }
    }

    
}