package com.example.Client.ChatroomWindow;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import javax.swing.*;

import com.example.Helpers.*;

public class ChatWindowPanel extends JPanel implements Observer{
    private ChatHistoryInterface chatlog;
    private Message msg;
    private JPanel panel;
    private JPanel invisiblepanel;
    private GridBagConstraints maingbc, invisiblegbc;
    private Timer timer;
    public ChatWindowPanel(){
        setLayout(new GridBagLayout());
        maingbc = new GridBagConstraints();
        invisiblegbc = new GridBagConstraints();
        maingbc.weightx = 1;
        maingbc.anchor = GridBagConstraints.WEST;
        maingbc.gridwidth = GridBagConstraints.REMAINDER;
        invisiblegbc.weighty = 1;
        invisiblepanel = new JPanel();
        timer = new Timer(10, e -> {
            revalidate();
            timer.stop();
        });
    }
    public void update(Object obj){
        if (obj instanceof ChatHistoryInterface){
            chatlog = (ChatHistoryInterface)obj;
            removeAll();
            for(Message m: chatlog.getHistory()){
                try {
                    SwingUtilities.invokeAndWait(()->{
                        if (m instanceof TextMessage){
                            panel = new TextPanel(((TextMessage)m)); 
                            add(panel, maingbc);
                        } else if (m instanceof ImageMessage){
                            panel = new ImagePanel(((ImageMessage)m),getWidth());
                            add(panel, maingbc);
                        }
                        add(invisiblepanel, invisiblegbc);
                    });
                } catch (InvocationTargetException | InterruptedException e) {
                    e.printStackTrace();
                }
                SwingUtilities.invokeLater(() -> {
                    repaint();
                    revalidate();
                });
            }
            SwingUtilities.invokeLater(() -> {
                repaint();
                revalidate();
            });
        } else if(obj instanceof Message){
            try {
                SwingUtilities.invokeAndWait(()->{
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
                    SwingUtilities.invokeLater(()-> {
                        repaint();
                        revalidate();
                    });
                });
            } catch (InvocationTargetException | InterruptedException e) {
                e.printStackTrace();
            }
            SwingUtilities.invokeLater(()-> {
                repaint();
                revalidate();
            });
        }
        
        timer.start();
    }
}
