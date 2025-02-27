package com.example;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import javax.imageio.ImageIO; 
import javax.swing.JFileChooser;
import javax.swing.JOptionPane; 
import javax.swing.filechooser.FileNameExtensionFilter;


public class ChatroomController{
    private ChatroomModel chatroomModel; 
    private String roomName; 
    private String username; 
    private ChatroomGUI chatroomGUI; 
    private ClientNetwork clientNetwork; 
    private JFileChooser fc; 
    
    /**
     * Constructor initializes the chatroom model and GUI using the builder pattern.
     * Requests initial data from the ChatroomModel.
     * @param roomName The name of the chatroom.
     */
    public ChatroomController(String roomName, String username) {
        this.roomName = roomName;
        this.username = username;

        this.chatroomModel = new ChatroomModel(username, roomName, UserAction.SELECT);
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
            clientNetwork.sendMessage(new TextMessage(username, roomName, messageText));
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
                clientNetwork.sendMessage(new ImageMessage(username, roomName, bytes));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Unexpected error...", "Warning", JOptionPane.PLAIN_MESSAGE);
            }
            System.out.println("Image selected: " + selectedFile.getAbsolutePath());
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


