package com.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * ChatRoomModel manages the data for a specific chatroom.
 * It handles messages, the user list, and communicates with ClientNetwork.
 * Implements Observable to notify ChatRoomController and GUI about updates.
 */
public class ChatRoomModel extends Observable {
    private String roomName; // Name of the chatroom.
    private List<String> messages; // List of messages in the chatroom.
    private List<String> users; // List of users in the chatroom.
    private ClientNetwork clientNetwork; // Handles communication with the server, ClientNetwork is a singelton.

    /**
     * Constructor initializes the chatroom model.
     * @param roomName The name of the chatroom.
     */
    public ChatRoomModel(String roomName) {
        this.roomName = roomName;
        this.messages = new ArrayList<>();
        this.users = new ArrayList<>();
        this.clientNetwork = ClientNetwork.getInstance();
    }

    /**
     * Requests initial room data from the server via ClientNetwork.
     * Triggers an update when data is received.
     */
    public void requestRoomData() {
        System.out.println("Requesting room data for: " + roomName); // Debug
        clientNetwork.requestRoomData(roomName);
    }

    /**
     * Sends a text message to the server via ClientNetwork.
     * @param message The text message to send.
     */
    public void sendMessage(String message) {
        System.out.println("Sending message: " + message); // Debug
        clientNetwork.sendMessage(message);
    }

    /**
     * Sends an image file to the server via ClientNetwork.
     * @param file The image file to send.
     */
    public void sendImage(File file) {
        System.out.println("Sending image: " + file.getAbsolutePath()); // Debug
        clientNetwork.sendImage(file);
    }

    /**
     * Updates the chatroom data when new data is received from the server.
     * Notifies observers (ChatRoomController & GUI).
     * @param messages The updated list of chat messages.
     * @param users The updated list of users in the chatroom.
     */
    public void updateRoomData(List<String> messages, List<String> users) {
        this.messages = messages;
        this.users = users;
        System.out.println("Room data updated. Notifying observers..."); // Debug
        notify(this); // Notify observers that data has changed.
    }

    /**
     * Returns the current list of messages in the chatroom.
     * @return List of chat messages.
     */
    public List<String> getMessages() {
        return messages;
    }

    /**
     * Returns the current list of users in the chatroom.
     * @return List of users.
     */
    public List<String> getUsers() {
        return users;
    }
}
