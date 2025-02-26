package com.example;
import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.*;

public class UserWindowPanel extends JPanel implements Observer{
    ChatroomInterface chatroom;
    JTextArea name;
    public UserWindowPanel(){
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(50,300));
        add(Box.createVerticalGlue());
    }
    public void update(Object obj){
        chatroom = (ChatroomInterface)obj;
        removeAll();
        for(String u : chatroom.getUsers()){
            name = new JTextArea(u);
            name.setEditable(false);
            add(name);
            repaint();
            revalidate();
        }
    }
    /* 
    public void addText(String text){
        add(new JTextArea(text));
        repaint();
        revalidate();
    } */
}
