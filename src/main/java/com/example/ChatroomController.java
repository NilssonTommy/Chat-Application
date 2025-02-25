package com.example;
import java.io.File; // Handling of images.
import java.util.List; // Handling of Lists.
import javax.swing.JFileChooser; // Needed to display a file picker when uploading images.
import javax.swing.filechooser.FileNameExtensionFilter; // Allows us to filter out invalid file formats when the user selects an image.

public class ChatroomController implements Observer {
    private ChatRoomModel chatroomModel; // Handles chat data and communication with ClientNetwork.
    private String roomName; // The name of the chatroom.
    private ChatroomGUI chatroomGUI; // The graphical user interface for the chatroom. 
    
    /**
     * Constructor initializes the chatroom model and GUI using the builder pattern.
     * Requests initial data from the ChatRoomModel.
     * @param roomName The name of the chatroom.
     */
    public ChatroomController(String roomName) {
        this.roomName = roomName;

        // Create the ChatRoomModel (which handles ClientNetwork communication)
        this.chatroomModel = new ChatRoomModel(roomName);
        chatroomModel.addSubscriber(this); // Register as observer.

        // Create a Builder and Director
        ChatroomBuilder builder = new BasicChatroomBuilder();
        ChatroomDirector director = new ChatroomDirector();

        // Director constructs the GUI using the Builder
        director.ConstructChatroomGUI(builder);

        // Retrieve the finished GUI instance
        this.chatroomGUI = builder.getResult();

        // Add chat window and user window as observers
        chatroomModel.addSubscriber(chatroomGUI.getChatwindow());
        chatroomModel.addSubscriber(chatroomGUI.getUserwindow());

        // Connect GUI buttons to their respective functions
        chatroomGUI.setSendbuttonListener(e -> sendMessage());
        chatroomGUI.setImagebuttonListener(e -> sendImage());

        // Request chatroom data (messages + user list) from ChatRoomModel
        chatroomModel.requestRoomData();
    }

    /**
     * Sends a text message via ChatRoomModel.
     */
    private void sendMessage() {
        String messageText = chatroomGUI.getTextfield().getText().trim();
        if (!messageText.isEmpty()) { // Ensure the message field is not empty
            chatroomModel.sendMessage(messageText); // Send message via the model
            chatroomGUI.getTextfield().setText(""); // Clear the text field after sending
        }
    }

    /**
     * Opens a file chooser and sends the selected image via ChatRoomModel.
     */
    private void sendImage() {
        JFileChooser fileChooser = new JFileChooser(); // Opens a file selection dialog
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "png", "jpg", "jpeg", "gif"); // Restrict file types
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(null); // Wait for user to select a file
        
        if (returnValue == JFileChooser.APPROVE_OPTION) { // If a file is chosen
            File selectedFile = fileChooser.getSelectedFile(); // Get selected file
            System.out.println("Image selected: " + selectedFile.getAbsolutePath()); // Debug
            chatroomModel.sendImage(selectedFile); // Send the image via the model
        } else {
            System.out.println("Image selection canceled."); // Debug
        }
    }

    /**
     * Handles updates from ChatRoomModel (Observer Pattern).
     * @param obj The update notification from the observed object.
     */
    @Override
    public void update(Object obj) {
        System.out.println("Update received: " + obj);
    }
}


