package com.example.Client.ChatClientWindow;
import com.example.Client.ChatroomWindow.ChatroomController;
import com.example.Client.ChatroomWindow.ChatroomModel;
import com.example.Client.ClientNetwork.ClientNetwork;
import com.example.Client.LoginWindow.UserResponse;
import com.example.Helpers.*;
import javax.swing.JOptionPane;



/**
 * ChatClientController handles the user's session after login.
 * The class is responsible for handling chatroom selection, 
 * as well as initializing ChatClientModel and ChatClientGUI.
 * This class is an observer of ClientNetwork (server communication).
 */
public class ChatClientController implements Observer {
    
    private ChatClientModel chatClientModel;
    private ChatClientGUI chatClientGUI;
    private ClientNetwork clientNetwork;
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
        this.clientNetwork = ClientNetwork.getInstance();
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
            createRoom(room);

            });
    }

    private void selectRoomListener(){
        chatClientGUI.addRoomSelectionListener(e -> {
            String selected = chatClientGUI.getSelectedRoom(); 
            if (selected != null) {
                roomName = selected; 
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
            joinRoom(room);
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

            createFailed();
        }
    }

    public void joinRoom(String room) {
        if (!chatClientModel.getChatrooms().contains(room)) { 
            if (clientNetwork != null) {
                clientNetwork.checkRoom(new ChatroomModel(user, room, UserAction.JOIN));
            }
        } else {
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
            default:
            try {
                throw new Exception("UserAction is incorrect");
            } catch (Exception e) {
                e.printStackTrace();
            }
       }
    }
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