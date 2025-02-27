package com.example;
import javax.swing.JOptionPane;

/**
 * ChatClientController handles the user's session after login.
 * The class is responsible for handling chatroom selection, 
 * as well as initializing ChatClientModel and ChatClientGUI.
 * This class is an observer of ClientNetwork (server communication).
 */
public class ChatClientController implements Observer {
    
    private ChatClientModel chatClientModel; // Stores user-related data.
    private ChatClientGUI chatClientGUI; // User interface after login.
    private ClientNetwork clientNetwork; // The singleton instance of ClientNetwork.
    private UserResponse user;
    private String roomName;
 
    /**
     * Constructor that receives a validated username from the LoginController.
     * Initializes ChatClientModel and ChatClientGUI.
     * Registers ChatClientController as an observer of ClientNetwork.
     * @param username The validated username of the logged in user.
     */
    public ChatClientController(UserResponse user) {
        this.user = user;
        this.clientNetwork = ClientNetwork.getInstance(); // Get the singleton instance of ClientNetwork.
        initObservable();
        this.chatClientModel = new ChatClientModel(user);
        this.chatClientGUI = new ChatClientGUI(chatClientModel);
        selectRoomListener();
        joinRoomListener();
        createRoomListener();
        addRoomListener();
    }   


    private void createRoomListener(){
        chatClientGUI.addCreateRoomListener(listener -> {
            String room = JOptionPane.showInputDialog("Enter room name");
            System.out.println("Creating room: " + room);
           // chatClientModel.addChatroom(roomName);   
            //chatClientGUI.updateRoomList();
            System.out.println(room);
            createRoom(room);

            });
    }

    private void selectRoomListener(){
        chatClientGUI.addRoomSelectionListener(e -> {
            String selected = chatClientGUI.getSelectedRoom(); 
            if (selected != null) {
                roomName = selected; 
                System.out.println("Selected room: " + roomName);
            } else {
                System.out.println("Inget rum valt.");
            }
        });
    }

    private void joinRoomListener(){
        chatClientGUI.addJoinRoomListener(listener -> {
            if (roomName != null) {
                System.out.println("Joining room: " + roomName);
                new ChatroomController(roomName, user);
            } else {
                System.out.println("Välj ett rum innan du går med!");
            }
        });
    }


    private void addRoomListener(){
        chatClientGUI.addAddRoomListener(listener -> {
            String room = JOptionPane.showInputDialog("Enter room name");
            System.out.println("Adding room: " + room);
            joinRoom(room);
           // chatClientModel.addChatroom(roomName);   
            //chatClientGUI.updateRoomList();
        });
    }
    
    /**
     * Creates a new chatroom and adds it to the model and send it to the server.
     * @param room The chatroom name of the new room.
     */
    public void createRoom(String room) {
        if (!chatClientModel.getChatrooms().contains(room)) { 
            if (clientNetwork != null) {
                clientNetwork.checkRoom(new ChatroomModel(user, room, UserAction.CREATE));
            }
        } else {
            System.out.println("Chatroom '" + room + "' already exists."); // Debug
            createFailed();
        }
    }

    public void joinRoom(String room) {
        if (!chatClientModel.getChatrooms().contains(room)) { 
            if (clientNetwork != null) {
                clientNetwork.checkRoom(new ChatroomModel(user, room, UserAction.JOIN));
            }
        } else {
            System.out.println("Chatroom '" + room + "' already exists in your list."); // Debug
            roomOnList();
        }
    }
    
    /**
     * Handles updates from observed objects (Observable).
     * @param obj The update notification from the observed object.
     */
    @Override
    public void update(Object obj) {
        if(obj instanceof ChatroomModel){
        ChatroomModel chatroom = (ChatroomModel) obj;
        System.out.println("Chatroom: " + chatroom.getRoomName() + " Status: " + chatroom.getStatus());

       switch(chatroom.getAction()){
        
            case JOIN:
                if(chatroom.getStatus()){
                    chatClientModel.addChatroom(chatroom.getRoomName());
                    chatClientGUI.updateRoomList();
                }else  {
                    joinFailed();
                }
                break;
            case CREATE:
                if(chatroom.getStatus()){
                    chatClientModel.addChatroom(chatroom.getRoomName());
                    chatClientGUI.updateRoomList();
                }else {
                    createFailed();
               }
                break;
       }
    }
        /* 
        if (obj instanceof List<?>) { // Check if object is a list.
            List<?> rawList = (List<?>) obj;

            // Ensure all elements are strings
            boolean allStrings = rawList.stream().allMatch(e -> e instanceof String);
            
            if (allStrings) {
                @SuppressWarnings("unchecked")
                List<String> chatrooms = (List<String>) rawList;
                
                chatClientModel.setChatrooms(chatrooms); // Update model

                System.out.println("Updated chatrooms: " + chatrooms); // Debug

                // Refresh the GUI once implemented
                if (chatClientGUI != null) {
                    // chatClientGUI.refresh(); // Uncomment once implemented
                }
            } else {
                System.out.println("Received a list, but not of type List<String>"); // Debug
            }
        } else {
            System.out.println("Received an update that is not a list"); // Debug
        }*/
 }

    private void initObservable(){
        clientNetwork.getClientRunnable().getObservableMap().addSubscriber("chatClientController", this);
    }

    public void createFailed(){
        JOptionPane.showMessageDialog(null,"This room name is already in use.", "", JOptionPane.PLAIN_MESSAGE);
    }

    public void joinFailed(){
        JOptionPane.showMessageDialog(null,"This room does not exist.", "", JOptionPane.PLAIN_MESSAGE);
    }

    public void roomOnList(){
        JOptionPane.showMessageDialog(null,"You already have this room on your list.", "", JOptionPane.PLAIN_MESSAGE);
    }
}