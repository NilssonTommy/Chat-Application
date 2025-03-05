package com.example.Helpers;

import javax.swing.*;

import com.example.Client.ChatroomWindow.ChatWindowPanel;
import com.example.Client.ChatroomWindow.ChatroomGUI;
import com.example.Client.ChatroomWindow.UserWindowPanel;

public interface ChatroomBuilder {
    void createChatWindow(ChatWindowPanel chatwindow);
    void createChatScroll(JScrollPane chatscroller);
    void createSendbutton(JButton sendbutton);
    void createTextfield(JTextField textfield);
    void createImagebutton(JButton imagebutton);
    void createUserWindow(UserWindowPanel userwindow);
    void createUserScroll(JScrollPane userscroll);
    
    ChatroomGUI getResult();
}
