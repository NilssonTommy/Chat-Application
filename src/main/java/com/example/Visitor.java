package com.example;

public interface Visitor {

    void visit(UserRequest user);
    void visit(Message msg);
    void visit(ChatroomModel model);
}
