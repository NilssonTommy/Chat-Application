package com.example;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Cursor;


public class LoginWindow extends JFrame {
    private JButton LoginButton;
    private JLabel RegisterButton;
    private JTextField username;
    

    public LoginWindow(){
        setTitle("login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel login = new JPanel();
        login.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        username = new JTextField(15);
        LoginButton = new JButton("Login");

        RegisterButton = new JLabel("<html><u>Register</u></html>");
        RegisterButton.setForeground(Color.BLUE);
        RegisterButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        RegisterButton.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    RegisterButton.setForeground(new Color(0, 0, 139)); 
                }

                public void mouseReleased(MouseEvent e) {
                    RegisterButton.setForeground(Color.BLUE);
                }
            });

        login.add(new JLabel("Username:"));
        login.add(username);
        login.add(LoginButton);
        login.add(RegisterButton);
        add(login);
        setVisible(true);
    } 

    public void addLoginListener(ActionListener listener) {
        LoginButton.addActionListener(listener);
    }

    public void addRegisterListener(MouseListener listener) {
        RegisterButton.addMouseListener(listener);
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

