package com.example;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * ChatRoomController hanterar logiken för det valda chattrummet.
 * Den initierar och hanterar ChatRoomModel samt kopplar den 
 * till ChatroomGUI som skapas via Builder Pattern.
 */
public class ChatroomController{
    private ChatroomModel chatroomModel; // Hanterar datan och kommunikation med servern (via ClientNetwork) för det chattrummet.
    private String roomName; // Chattrummets namn.
    private ChatroomGUI chatroomGUI; // Användarens gränssnitt för chattrummet.
    private ClientNetwork clientNetwork; // Singelton-instans.

    /**
     * Konstruktor som initierar chattrummets model med chattrummets namn.
     * Skapar chattrummets GUI genom builder design pattern.
     * @param roomName Namnet på ett specifitk chattrum.
     */
    public ChatroomController(String roomName){
        this.roomName = roomName;
        this.chatroomModel = new ChatroomModel(roomName);
        this.clientNetwork = ClientNetwork.getInstance();

        // Skapa en Builder och Director
        ChatroomBuilder builder = new BasicChatroomBuilder();
        ChatroomDirector director = new ChatroomDirector();

        // Directorn konstruerar GUI:t med Buildern
        director.ConstructChatroomGUI(builder);

        // Hämta den färdiga GUI-instansen
        this.chatroomGUI = builder.getResult();

        // Koppla GUI: chatWindow och userWindow som observers till modellen
        chatroomModel.addSubscriber(chatroomGUI.getChatwindow());
        chatroomModel.addSubscriber(chatroomGUI.getUserwindow());

        // Koppla GUI:s knappar för att skicka text och bild till deras funktioner
        chatroomGUI.setSendbuttonListener(e -> sendMessage());
        chatroomGUI.setImagebuttonListener(e -> sendImage()); 
    }

    /*
     * Skickar ett meddelande genom att be ChatRoomModel att hantera det.
     */
    private void sendMessage(){
        String messageText = chatroomGUI.getTextfield().getText().trim();
        if(!messageText.isEmpty()){ // Om det finns text i meddelandefältet.
            clientNetwork.sendMessage(messageText); // Skickar meddelandet till modellen som hanterar server-kommunikation.
            chatroomGUI.getTextfield().setText(""); // Rensar textfältet efter att meddelandet skickas.
        }
    }

    /*
     * Skickar en bild genom att öppna en filväljare och skicka den valda filen (bilden) genom att be ChatRoomModel att hantera det.
     */
    private void sendImage(){
        JFileChooser fileChooser = new JFileChooser(); // Låter användaren välja en fil.
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "png", "jpg", "jpeg", "gif"); // Filtrerar bort ogiltiga filformat.
        fileChooser.setFileFilter(filter); // Filformatfiltret tillämpas på fileChooser.

        int returnValue = fileChooser.showOpenDialog(null); // Öppnar filväljaren så att användaren kan välja en fil.
        
        if(returnValue == JFileChooser.APPROVE_OPTION){ // Om användaren valde en fil.
            File selectedFile = fileChooser.getSelectedFile(); // Lagrar vald fil i selectedFile.
            System.out.println("Bild vald: " + selectedFile.getAbsolutePath()); // Debug
            chatroomModel.sendImage(selectedFile); // Skickar bilden till modellen som hanterar server-kommunikation.
        } else{
            System.out.println("Bildval avbröts."); // Debug
        }
    }

}
