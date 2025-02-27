package com.example;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;

public class TestWindow extends JFrame {


    public TestWindow() {
        // Setup window properties

        PortalConnection db = PortalConnection.getInstance();
        if (db.getConnection() != null) {
            System.out.println("✅ Connected to PostgreSQL successfully!");
        } else {
            System.out.println("❌ Failed to connect!");
        }

        setTitle("Chat Log");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Center window on screen
        setResizable(false);

        // Panel to hold chat messages
        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS)); // Vertically stacked messages
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

     try {
        byte[]imageBytes = Files.readAllBytes(Paths.get("C:/Users/User/Pictures/IMG_0874.png"));
        db.addMsg("Bob", "billld", currentTimestamp, "Grupp5");
        db.addImgMsg("Bob", imageBytes, currentTimestamp, "Grupp5");
        System.out.println("Successful");
        
       } catch (IOException e) {
       }
    
        // Get chat log from PortalConnection (assuming you have this method)
        List<Message> chatLog = db.getChatLog("Grupp5"); // Replace "general" with the actual room name

        // Add each message (text or image) to the chat panel
        for (Message msg : chatLog) {
            if (msg instanceof TextMessage) {
                // Display text message
                TextMessage textMsg = (TextMessage) msg;
                JTextArea textArea = new JTextArea(textMsg.getAuthor() + ": " + textMsg.getContent());
                textArea.setEditable(false);
                textArea.setBackground(Color.LIGHT_GRAY);
                chatPanel.add(textArea); // Add text message to the panel
            } else if (msg instanceof ImageMessage) {
                // Display image message
                ImageMessage imageMsg = (ImageMessage) msg;
                Image img = imageMsg.returnImg();

                int width = 200;  // Desired width
                int height = 200; // Desired height
                Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);

                ImageIcon imageIcon = new ImageIcon(resizedImg);
                JLabel imageLabel = new JLabel(imageIcon);
                chatPanel.add(imageLabel); // Add image message to the panel
            }
        }

        // Add the chat panel to the frame
        JScrollPane scrollPane = new JScrollPane(chatPanel); // Make the panel scrollable
        add(scrollPane);

        // Make the window visible
        setVisible(true);
    }

    public static void main(String[] args) {


        // Create and show the chat window
        new TestWindow();
    }
}
