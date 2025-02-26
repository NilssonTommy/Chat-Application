package com.example;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
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

        // Sätt en finare font för listan
        roomList.setFont(new Font("Serif", Font.PLAIN, 24));
        roomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    

        // Centrera texten i listan
        DefaultListCellRenderer renderer = (DefaultListCellRenderer)roomList.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Lägg listan i en JScrollPane
        JScrollPane scrollPane = new JScrollPane(roomList);
        // Sätt en rimlig storlek så att den inte fyller hela mitten
        scrollPane.setPreferredSize(new Dimension(300, 200));

        // För att verkligen centrera scrollPane kan vi använda en panel med t.ex. GridBagLayout
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(scrollPane); // GridBagLayout lägger scrollPane i mitten som standard

        // Lägg centerPanel i mitten av BorderLayout
        contentPane.add(centerPanel, BorderLayout.CENTER);

        // Lägg knapparna i en panel längst ned
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


    void addRoomSelectionListener(ListSelectionListener listener){
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
    

    /* 

  
    private void createRoomListener(){
            gui.addCreateRoomListener(listener -> {
            String roomName = JOptionPane.showInputDialog("Enter room name");
            System.out.println("Creating room: " + roomName);
            model.addChatroom(roomName);   
            gui.updateRoomList();
            });
}

 private void selectRoomListener(){
    gui.addRoomSelectionListener(e -> {
        String selected = gui.getSelectedRoom(); // ✅ Få det valda rummet
        if (selected != null) {
            roomName = selected; // ✅ Uppdatera `roomName`
            System.out.println("Selected room: " + roomName);
        } else {
            System.out.println("Inget rum valt.");
        }
    });
}

private void joinRoomListener(){
    gui.addJoinRoomListener(listener -> {
        if (roomName != null) {
            System.out.println("Joining room: " + roomName);
        } else {
            System.out.println("Välj ett rum innan du går med!");
        }
    });
}


    private void addRoomListener(){
        gui.addAddRoomListener(listener -> {
            roomName = JOptionPane.showInputDialog("Enter room name");
            System.out.println("Adding room: " + roomName);
            model.addChatroom(roomName);   
            gui.updateRoomList();
        });
    }
    /**
    */


}


