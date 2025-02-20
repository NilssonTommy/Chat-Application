package com.example;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.filechooser.FileFilter;
import java.util.*;
import java.util.List;

import javax.imageio.ImageIO;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 * Testklass för att testa guin.
 */
public class testclass{
    static JFileChooser fc;
    public static void main(String[] arg){
        ChatroomBuilder builder = new BasicChatroomBuilder();
        ChatroomDirector director = new ChatroomDirector();
        director.ConstructChatroomGUI(builder);
        ChatroomGUI gui = builder.getResult();
        List<String> users = new LinkedList<>();
        //gui.getUserwindow().addText("hej");
        //gui.getUserwindow().addText("test");
        /*  
        for(int i=0; i<15; i++){
            users.add("hej");
        } 
        gui.getUserwindow().update(users); */
        /*gui.getChatwindow().addImg(ImageIO.read(new File("/Users/jespernyberg/Pictures/icon.png")));
        gui.getChatwindow().addImg(ImageIO.read(new File("/Users/jespernyberg/Pictures/icon.png")));
        //Thread.sleep(200);
        gui.chatwindow.revalidate();*/
        gui.setSendbuttonListener(e -> {
            TextMessage msg = new TextMessage("John", gui.getTextfield().getText());
            if (msg.getContent().length() != 0){
                gui.getChatwindow().update(msg);
                gui.getUserwindow().update(gui.getTextfield().getText());
                gui.getTextfield().setText("");
                SwingUtilities.invokeLater(() ->{
                    gui.getChatwindow().revalidate();
                });
            }
            gui.getChatwindow().revalidate();
        });
        
        gui.setImagebuttonListener(e -> {
            
            if (fc == null){
                fc = new JFileChooser();
                fc.setCurrentDirectory(new File("/Users/jespernyberg/Pictures"));
            }                
            FileFilter imageFilter = new FileNameExtensionFilter("Bilder", ImageIO.getReaderFileSuffixes());
            fc.setFileFilter(imageFilter);
            int returnval = fc.showOpenDialog(null);
            if (returnval == JFileChooser.APPROVE_OPTION){
                File file = fc.getSelectedFile();
                try {
                    BufferedImage img = ImageIO.read(file);
                    gui.getChatwindow().update(new ImageMessage("Bilder", img));
                } catch(Exception e2){
                    JOptionPane.showMessageDialog(null, "Oväntat fel inträffade.", "Varning!", JOptionPane.PLAIN_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingen fil valdes.", "Varning!", JOptionPane.PLAIN_MESSAGE);
            }     
        });
        SwingUtilities.invokeLater(() ->{
            gui.getChatwindow().revalidate();
        });
    }
}
        

        //gui.chatscroll.setPreferredSize(gui.chatscroll.getPreferredSize());
        //gui.chatwindow.setPreferredSize(gui.chatwindow.getPreferredSize());
        //gui.getChatwindow().addGlue();
        //gui.getChatwindow().addText("101010101010101010");
       


