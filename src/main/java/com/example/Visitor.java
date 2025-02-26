package com.example;

public interface Visitor {

    void visit(UserRequest user);

    void visit(ChatroomModel model);

}
