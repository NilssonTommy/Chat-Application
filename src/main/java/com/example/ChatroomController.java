package com.example;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File; // Handling of images.

import javax.imageio.ImageIO;
import javax.swing.JFileChooser; // Handling of Lists.
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter; // Needed to display a file picker when uploading images.

/* TODO: Change the way which sendImage and sendMessage are travelling. 
From ChatroomController -> ChatRoomModel -> ClientNetwork
To ChatroomController -> ClientNetwork

*/
public class ChatroomController implements Observer {
    private ChatRoomModel chatroomModel; // Handles chat data and communication with ClientNetwork.
    private String roomName; // The name of the chatroom.
    private String username; // The name of the user which sends a message.
    private ChatroomGUI chatroomGUI; // The graphical user interface for the chatroom.
    private ClientNetwork clientNetwork; // Singelton-instance.
    private JFileChooser fc; 
    
    /**
     * Constructor initializes the chatroom model and GUI using the builder pattern.
     * Requests initial data from the ChatRoomModel.
     * @param roomName The name of the chatroom.
     */
    public ChatroomController(String roomName, String username) {
        this.roomName = roomName;

        this.username = username;

        // Create the ChatRoomModel (which handles ClientNetwork communication)
        this.chatroomModel = new ChatRoomModel(roomName);
        clientNetwork.addSubscriber(chatroomModel); // Register chatroomModel as observer to clientNetwork.

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

        this.clientNetwork = ClientNetwork.getInstance(); // Get the singleton instance of ClientNetwork.
        clientNetwork.addObserver(chatroomModel); // Register this controller as an observer of ClientNetwork.

        clientNetwork.requestRoomData(roomName);

        chatroomModel.setObservers(chatroomGUI.getChatwindow(), chatroomGUI.getUserwindow());
    }

    /**
     * Sends a text message via ChatRoomModel.
     */
    private void sendMessage() {
        String messageText = chatroomGUI.getTextfield().getText();
        if (!messageText.isEmpty()) { // Ensure the message field is not empty
            clientNetwork.sendMessage(new TextMessage(messageText, username)); // Send message via the model.
            chatroomGUI.getTextfield().setText(""); // Clear the text field after sending.
        }
    }

    /**
     * Opens a file chooser and sends the selected image via ChatRoomModel.
     */
    private void sendImage() {
        if(fc == null){
            fc = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", ImageIO.getReaderFileSuffixes()); // Restrict file types
            fc.setFileFilter(filter);
        }
        int returnValue = fc.showOpenDialog(null); // Wait for user to select a file
        
        if (returnValue == JFileChooser.APPROVE_OPTION) { // If a file is chosen
            File selectedFile = fc.getSelectedFile(); // Get selected file
            try {
                BufferedImage img = ImageIO.read(selectedFile);
                clientNetwork.sendMessage(new ImageMessage(username, img)); // Send the image via the ClientNetwork.
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Unexpected error...", "Warning", JOptionPane.PLAIN_MESSAGE);
            }
            System.out.println("Image selected: " + selectedFile.getAbsolutePath()); // Debug
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


