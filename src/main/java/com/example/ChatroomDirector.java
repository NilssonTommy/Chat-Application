package main.java.com.example;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ChatroomDirector {
    
    public void ConstructChatroomGUI(ChatroomBuilder builder){
        builder.createChatScroll(new JScrollPane());
        builder.createChatWindow(new ChatWindowPanel());
        builder.createSendbutton(new JButton("Send Message"));
        builder.createImagebutton(new JButton("Send Image"));
        builder.createTextfield(new JTextField(20));
        builder.createUserScroll(new JScrollPane());
        builder.createUserWindow(new UserWindowPanel());
    }
}
