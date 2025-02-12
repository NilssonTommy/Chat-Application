import java.util.*;

public class ChatClientModel implements ChatClientInterface {
    private String username;
    private List<String> chatrooms;

    public ChatClientModel(String username){
        this.username = username;
        chatrooms = new LinkedList<String>();
    }

    public String getUsername(){
        return username;
    }

    public List<String> getChatrooms(){
        return chatrooms;
    }

    public void setChatrooms(List<String> chatrooms){
        if (this.chatrooms.size() == 0){
            this.chatrooms = chatrooms;
        } else {
            this.chatrooms.addAll(chatrooms);
        }
    }

    public void addChatroom(String chatroom){
        chatrooms.add(chatroom);
    }
}
