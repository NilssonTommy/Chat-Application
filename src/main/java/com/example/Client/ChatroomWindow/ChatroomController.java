package com.example.Client.ChatroomWindow;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import javax.imageio.ImageIO; 
import javax.swing.JFileChooser;
import javax.swing.JOptionPane; 
import javax.swing.filechooser.FileNameExtensionFilter;

import com.example.Client.ClientNetwork.ClientNetwork;
import com.example.Helpers.*;


public class ChatroomController{
    private ChatroomModel chatroomModel;
    private String roomName; 
    private UserInterface user; 
    private ChatroomGUI chatroomGUI; 
    private ClientNetwork clientNetwork; 
    private JFileChooser fc; 
    
    /**
     * Constructor initializes the chatroom model and GUI using the builder pattern.
     * Requests initial data from the ChatroomModel.
     * @param roomName The name of the chatroom.
     */
    public ChatroomController(String roomName, UserInterface user) {
        this.roomName = roomName;
        this.user = user;

        this.chatroomModel = new ChatroomModel(user, roomName, UserAction.SELECT);

        ClientNetwork.getInstance().getClientRunnable().getObservableMap().addSubscriber(roomName, chatroomModel);

        ChatroomBuilder builder = new BasicChatroomBuilder();
        ChatroomDirector director = new ChatroomDirector();

        director.ConstructChatroomGUI(builder);

        this.chatroomGUI = builder.getResult();

        chatroomGUI.setSendbuttonListener(e -> sendMessage());
        chatroomGUI.setImagebuttonListener(e -> sendImage());

        this.clientNetwork = ClientNetwork.getInstance();

        clientNetwork.requestRoomData(chatroomModel);

        chatroomModel.setObservers(chatroomGUI.getChatwindow(), chatroomGUI.getUserwindow());
    }

    private void sendMessage() {
        String messageText = chatroomGUI.getTextfield().getText();
        if (!messageText.isEmpty()) {
            clientNetwork.sendMessage(new TextMessage(user.getUsername(), roomName, messageText));
            chatroomGUI.getTextfield().setText("");
        }
    }

    /**
     * Opens a file chooser and sends the selected image via ChatroomModel.
     */
    private void sendImage() {
        if(fc == null){
            fc = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", ImageIO.getReaderFileSuffixes());
            fc.setFileFilter(filter);
        }
        int returnValue = fc.showOpenDialog(null);
        
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();

            try {
                BufferedImage img = ImageIO.read(selectedFile);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(img, getFileExtension(selectedFile.getName()), baos);
                byte[] bytes = baos.toByteArray();
                clientNetwork.sendMessage(new ImageMessage(user.getUsername(), roomName, bytes));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Unexpected error...", "Warning", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }
    
    private String getFileExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int dotIndex = filename.lastIndexOf(".");
        if (dotIndex >= 0) {
            return filename.substring(dotIndex + 1);
        }
        return "";
    }
}


