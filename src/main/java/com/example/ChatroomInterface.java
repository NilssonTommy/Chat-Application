package com.example;
import java.util.List;

public interface ChatroomInterface {
    ChatHistoryInterface getChatLog();
    List<User> getUsers();
}
