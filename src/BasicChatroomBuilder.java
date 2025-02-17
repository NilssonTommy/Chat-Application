import javax.swing.*;


public class BasicChatroomBuilder implements ChatroomBuilder {
    JScrollPane chatscroller, userscroll;
    ChatWindowPanel chatwindow;
    JButton sendbutton, imagebutton;
    JTextField textfield;
    UserWindowPanel userwindow;

    public void createChatWindow(ChatWindowPanel chatwindow){
        this.chatwindow = chatwindow;
    }
    public void createChatScroll(JScrollPane chatscroller){
        this.chatscroller = chatscroller;
    }
    public void createSendbutton(JButton sendbutton){
        this.sendbutton = sendbutton;
    }
    public void createTextfield(JTextField textfield){
        this.textfield = textfield;
    }
    public void createImagebutton(JButton imagebutton){
        this.imagebutton = imagebutton;
    }
    public void createUserWindow(UserWindowPanel userwindow){
        this.userwindow = userwindow;
    }
    public void createUserScroll(JScrollPane userscroll){
        this.userscroll = userscroll;
    }
    public ChatroomGUI getResult(){
        return new ChatroomGUI(chatscroller, userscroll, chatwindow, userwindow, sendbutton, imagebutton, textfield);
    }

}
