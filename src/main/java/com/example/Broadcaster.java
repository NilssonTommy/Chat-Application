package com.example;

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
