package com.example;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

public class Broadcaster {
    private static Broadcaster instance;
    private ObservableMap obsMap;
    private Broadcaster(){
        obsMap = new ObservableMap();
    }
    public static Broadcaster getInstance(){
        if(instance == null){
            instance = new Broadcaster();
        }
        return instance;
    }
    public ObservableMap getObservable(){
        return obsMap;
    }
}
