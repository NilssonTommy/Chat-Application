package com.example;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

//Kommer att fixa design på layout nu så det ser snyggare ut

public class RegisterWindow extends JFrame {
    private JButton RegisterButton;
    private JTextField username;
    

    public RegisterWindow(){
        setTitle("Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Center window on screen
        setResizable(false);

        JPanel login = new JPanel();
        login.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        username = new JTextField(15);
        RegisterButton = new JButton("Register");

        login.add(new JLabel("Username:"));
        login.add(username);
        login.add(RegisterButton);
        add(login);
        setVisible(true);
    }

    public void addRegistrationListener(ActionListener listener) {
        RegisterButton.addActionListener(listener);
    }
    
    public void registrationComplete(){
        JOptionPane.showMessageDialog(null,"Registration successful!");
        closeRegisterWindow();
    }
    public void registrationFailed(){

        JOptionPane.showMessageDialog(null,"Registration failed, try again!");
    }

    public void closeRegisterWindow(){
        this.dispose();
    }

    public String getUsername() {
        return username.getText();
    }
}

