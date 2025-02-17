package com.example;
import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginWindow extends JFrame {
    private JButton loginbtn;
    private JTextField username;
    

    public LoginWindow(){
        setTitle("login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Center window on screen
        setResizable(false);

        JPanel login = new JPanel();
        login.setLayout(new FlowLayout());

        username = new JTextField(15);
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

    
    public void validUsername(){

        JOptionPane.showMessageDialog(null,"Login authentication success");
    }
    public void invalidUsername(){

        JOptionPane.showMessageDialog(null,"Login authentication failed");
    }

    public void closeLoginWindow(){
        this.dispose();
    }

    public String getUsername() {
        return username.getText();
    }
}

