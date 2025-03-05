package com.example.Client.ChatClientWindow;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionListener;

public class ChatClientGUI extends JFrame {

    private JList<String> roomList;
    private JButton joinButton;
    private JButton createButton;
    private JButton addButton;
    private ChatClientModel model;
    private DefaultListModel<String> listModel;

    public ChatClientGUI(ChatClientModel model){
        this.model = model;
        listModel = new DefaultListModel<>();
        setRoomList();

        init();
    }

    private void setRoomList() {
        List<String> chatrooms = model.getChatrooms();

        for (String room : chatrooms) {
            listModel.addElement(room);
        }
             roomList = new JList<>(listModel);
    }

   private void init() {
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        roomList.setFont(new Font("Serif", Font.PLAIN, 24));
        roomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
        DefaultListCellRenderer renderer = (DefaultListCellRenderer)roomList.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane scrollPane = new JScrollPane(roomList);
        scrollPane.setPreferredSize(new Dimension(300, 200));

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(scrollPane);

        contentPane.add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        joinButton = new JButton("Join Room");
        createButton = new JButton("Create Room");
        addButton = new JButton("Add Room");

        buttonPanel.add(joinButton);
        buttonPanel.add(createButton);
        buttonPanel.add(addButton);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        setTitle(model.getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(800,600));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void addCreateRoomListener(ActionListener listener) {
        createButton.addActionListener(listener);
    }

    public void addJoinRoomListener(ActionListener listener) {
        joinButton.addActionListener(listener);
    }

    public void addAddRoomListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void addRoomSelectionListener(ListSelectionListener listener){
        roomList.addListSelectionListener(listener);
    }
    
    public void updateRoomList() {
        listModel.clear();
    
        List<String> chatrooms = model.getChatrooms();
        for (String room : chatrooms) {
            listModel.addElement(room);
        }
        revalidate(); 
    }

    public String getSelectedRoom(){
        return roomList.getSelectedValue();
    }
    

}


