import java.awt.FlowLayout;
import java.awt.event.*;
import java.util.EventListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginWindow extends JFrame {
    private JButton loginbtn;

    public LoginWindow(){
        setTitle("login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Center window on screen
        setResizable(false);

        JPanel login = new JPanel();
        login.setLayout(new FlowLayout());

        JTextField username = new JTextField(15);
        loginbtn = new JButton("login");
        login.add(new JLabel("Username:"));
        login.add(username);
        login.add(loginbtn);
        add(login);
        setVisible(true);
    } 

    public void addLoginListener(ActionListener listener) {
        loginbtn.addActionListener(listener);
    }
    
    public static void main(String[] args) {
        new LoginWindow();
    }
    
}
