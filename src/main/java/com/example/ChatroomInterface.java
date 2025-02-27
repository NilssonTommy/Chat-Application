package com.example;
import java.io.Serializable;
import java.util.List;

public interface ChatroomInterface extends Serializable, Visitable, Observer {
    ChatHistoryInterface getChatLog();
    List<UserInterface> getUsers();
    String getRoomName();
    void setChatLog(ChatHistoryInterface chathistory);
    void setUsers(List<UserInterface> users);
    UserAction getAction();
    UserInterface getUser();
    void setStatus(Boolean b);
}
