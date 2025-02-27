package com.example;

import javax.swing.*;

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
