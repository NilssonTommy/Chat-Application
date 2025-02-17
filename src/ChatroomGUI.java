
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ChatroomGUI extends JFrame {
    JScrollPane chatscroll, userscroll;
    ChatWindowPanel chatwindow;
    JButton sendbutton, imagebutton;
    JTextField textfield;
    UserWindowPanel userwindow;
    JPanel bottompanel, centerpanel;
    private int prevmax_chat, prevmax_user;
    public ChatroomGUI(JScrollPane chatscroll, JScrollPane userscroll, ChatWindowPanel chatwindow, 
            UserWindowPanel userwindow, JButton sendbutton, JButton imagebutton, JTextField textfield){
        this.chatscroll = chatscroll;
        this.userscroll = userscroll;
        this.chatwindow = chatwindow;
        this.userwindow = userwindow;
        this.sendbutton = sendbutton;
        this.imagebutton = imagebutton;
        this.textfield = textfield;
        //Kanske får lägga detta i builder istället
        bottompanel = new JPanel();
        setupFrame();
        setupListeners();
    }
    private void setupFrame(){
        setLayout(new BorderLayout(5,5));
        chatscroll.setViewportView(chatwindow);
        add(chatscroll, BorderLayout.CENTER);
        userscroll.setViewportView(userwindow);
        add(userscroll, BorderLayout.EAST);
        bottompanel.setLayout(new FlowLayout());
        bottompanel.add(imagebutton);bottompanel.add(textfield);bottompanel.add(sendbutton);
        add(bottompanel, BorderLayout.SOUTH);
        setTitle("test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setVisible(true);
    }
    private void setupListeners(){
        chatscroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
            public void adjustmentValueChanged(AdjustmentEvent e) { 
                if (prevmax_chat != e.getAdjustable().getMaximum()){
                    chatwindow.revalidate();
                    e.getAdjustable().setValue(e.getAdjustable().getMaximum());
                    prevmax_chat = e.getAdjustable().getMaximum();
                }
                  
            }
        });
        userscroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
            public void adjustmentValueChanged(AdjustmentEvent e) { 
                if (prevmax_user != e.getAdjustable().getMaximum()){
                    userwindow.revalidate();
                    e.getAdjustable().setValue(e.getAdjustable().getMaximum());
                    prevmax_user = e.getAdjustable().getMaximum();
                }
                  
            }
        });
    }
    public ChatWindowPanel getChatwindow(){
        return chatwindow;
    }
    public UserWindowPanel getUserwindow(){
        return userwindow;
    }
    public JScrollPane getChatscroll(){
        return chatscroll;
    }
    public JTextField getTextfield(){
        return textfield;
    }
    public void setSendbuttonListener(ActionListener listener){
        sendbutton.addActionListener(listener);
    }
    public void setImagebuttonListener(ActionListener listener){
        imagebutton.addActionListener(listener);
    }
}
