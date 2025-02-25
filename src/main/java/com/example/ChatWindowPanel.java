package com.example;
import java.awt.*;

import javax.swing.*;

public class ChatWindowPanel extends JPanel implements Observer{
    private ChatHistoryInterface chatlog;
    private Message msg;
    private JPanel panel;
    private JPanel invisiblepanel;
    private GridBagConstraints maingbc, invisiblegbc;
    public ChatWindowPanel(){
        //setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setLayout(new GridBagLayout());
        maingbc = new GridBagConstraints();
        invisiblegbc = new GridBagConstraints();
        maingbc.weightx = 1;
        maingbc.anchor = GridBagConstraints.WEST;
        maingbc.gridwidth = GridBagConstraints.REMAINDER;
        invisiblegbc.weighty = 1;
        invisiblepanel = new JPanel();
    }
    public void update(Object obj){
        if (obj instanceof ChatHistoryInterface){
            chatlog = (ChatHistoryInterface)obj;
            removeAll();
            for(Message m: chatlog.getHistory()){
                if (m instanceof TextMessage){
                    panel = new TextPanel(((TextMessage)m)); 
                    add(panel);
                } else if (m instanceof ImageMessage){
                    panel = new ImagePanel(((ImageMessage)m),getWidth());
                    add(panel);
                }
                add(invisiblepanel, invisiblegbc);
                revalidate();
            }
        } else if(obj instanceof Message){
            msg = (Message)obj;
            remove(invisiblepanel);
            if (msg instanceof TextMessage){
                panel = new TextPanel(((TextMessage)msg));
                add(panel, maingbc);
            } else if (msg instanceof ImageMessage){
                panel = new ImagePanel(((ImageMessage)msg), getWidth());
                add(panel, maingbc);
            }
            add(invisiblepanel, invisiblegbc);
            repaint();
            revalidate();
            
        }
    }
}
