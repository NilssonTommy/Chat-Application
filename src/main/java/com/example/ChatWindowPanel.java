package com.example;
import java.awt.*;
import javax.swing.*;

public class ChatWindowPanel extends JPanel implements Observer{
    private ChatHistoryInterface chatlog;
    private JTextArea name;
    private Dimension verticalSpace = new Dimension(0,5);
    public ChatWindowPanel(){
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(Box.createVerticalGlue());

    }
    public void update(Object obj){
        chatlog = (ChatHistoryInterface)obj;
        removeAll();
        for(Message m: chatlog.getHistory()){
            add(Box.createRigidArea(verticalSpace));
            name = new JTextArea(m.getAuthor() + ":");
            name.setBackground(getBackground());
            name.setEditable(false);
            if (m instanceof TextMessage){
                this.add(new TextPanel(((TextMessage)m).getContent()) );
            } else if (m instanceof ImageMessage){
                this.add(new ImagePanel(((ImageMessage)m).getContent()));
            }
            add(Box.createRigidArea(verticalSpace));
            repaint();
            revalidate();
        }
    }
    /* 
    public void addText(String text){
        add(Box.createRigidArea(verticalSpace));
        name = new JTextArea("Test:");
        name.setBackground(getBackground());
        name.setEditable(false);
        add(name);
        add(new TextPanel(text));
        add(Box.createRigidArea(verticalSpace));
        repaint();
        revalidate();
    }
    public void addImg(Image img){
        add(Box.createRigidArea(verticalSpace));
        name = new JTextArea("Test:");
        name.setBackground(getBackground());
        name.setEditable(false);
        add(name);
        add(new ImagePanel(img));
        add(Box.createRigidArea(verticalSpace));
        add(Box.createVerticalBox());
        repaint();
        revalidate();
    }*/
}
