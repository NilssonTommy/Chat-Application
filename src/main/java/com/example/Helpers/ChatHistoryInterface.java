package com.example.Helpers;

import java.io.Serializable;
import java.util.*;

public interface ChatHistoryInterface extends Serializable{
    public void addMessage(Message msg);
    public List<Message> getHistory();
    public void setHistory(List<Message> chatlog);
}