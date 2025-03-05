package com.example.Helpers;

import com.example.Client.LoginWindow.UserRequest;

public interface Visitor {

    void visit(UserRequest user);
    void visit(Message msg);
    void visit(ChatroomInterface model);
}
