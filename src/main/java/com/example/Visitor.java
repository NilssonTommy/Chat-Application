package com.example;

public interface Visitor {

    void visit(UserRequest user);
    void visit(Message msg);
    void visit(ChatroomInterface model);
    //void visit(ChatroomModel model);

}
