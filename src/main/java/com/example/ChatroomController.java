package com.example;
public class ChatRoomController {

    //private ChatRoom chatRoom; // Referens till ChatRoom.
    //private ChatRoomGUI gui; // Referens till ChatRoomGUI som är interfacet för varje chatrum.
    private ClientNetwork clientNetwork; // Singleton-instansen av ClientNetwork.

    // Konstruktor
    public ChatRoomController(String roomName, ClientNetwork clientNetwork){
        this.clientNetwork = ClientNetwork.getInstance();
        clientNetwork.addObserver(this);

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * ChatRoomController hanterar logiken för det valda chattrummet.
 * Den initierar och hanterar ChatRoomModel och ChatroomGUI samt kopplar samman dem.
 */
public class ChatRoomController{
    private ChatRoomModel chatRoomModel; // Hanterar datan och kommunikation med servern (via ClientNetwork) för det chattrummet.
    private ChatroomGUI chatRoomGUI; // Användargränssnittet för chattrummet.
    private String roomName; // Chattrummets namn.

    public ChatRoomController(String roomName){
        this.roomName = roomName;
        this.chatRoomModel = new ChatRoomModel(roomName);
        this.chatRoomGUI = new ChatroomGUI(new JScrollPane(), 
            new JScrollPane(),
            new ChatWindowPanel(),
            new UserWindowPanel(),
            new JButton("Send"), // FRÅGA? Borde inte denna knappen namnges i chatRoomGUI?
            new JButton("Send Image"), // FRÅGA? Borde inte denna knappen namnges i chatRoomGUI?
            new JTextField()
        );

        // Lägger till chatwindow och userwindow som observer av modellen.
        chatRoomModel.addSubscriber(chatRoomGUI.getChatwindow());
        chatRoomModel.addSubscriber(chatRoomGUI.getUserwindow());

        // Kopplar skicka-text-knappen i GUI till metoden sendMessage().
        chatRoomGUI.setSendbuttonListener(e -> sendMessage());

        // Kopplar skicka-bild-knappen i GUI till metoden sendImage().
        chatRoomGUI.setImagebuttonListener(e -> sendImage());
    }

    /*
     * Skickar ett meddelande genom att be ChatRoomModel att hantera det.
     */
    private void sendMessage(){
        String messageText = chatRoomGUI.getTextfield().getText().trim();
        if(!messageText.isEmpty()) {
            chatRoomModel.sendMessage(messageText); // Skicka till modellen som hanterar kommunikatione med servern.
            chatRoomGUI.getTextfield().setText(""); // Rensa textfältet efter att meddelandet är skickat.
        }
    }

    /**
     * Öppnar en filväljare och skickar den valda filen (bilden) till modellen.
     */
    private void sendImage(){
        JFileChooser fileChooser = new JFileChooser(); // Skapar en filväljare så att användaren kan välja en fil (bild) att skicka.
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "png", "jpg", "jpeg", "gif"); // Begränsar filformat som kan skickas.
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(null); // Visar filväljaren och väntar på användarens val.
        
        // Kontrollerar om användaren valde en fil eller avbröt.
        if (returnValue == JFileChooser.APPROVE_OPTION) { 
            File selectedFile = fileChooser.getSelectedFile(); // Hämtar den valda filen.

            System.out.println("Bild vald: " + selectedFile.getAbsolutePath()); // Debug

            // Skickar bilden till modellen, som sedan skickar den till servern.
            chatRoomModel.sendImage(selectedFile);
        } else {
        System.out.println("Bildval avbröts."); // Debug
        }
    }

}
