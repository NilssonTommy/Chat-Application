package com.example;
import java.util.*;

/**
 * Observable class which handles different types of observers. 
 * Each type of observer is identified by a key when adding, removing och updating.
 */
public class ObservableChatroom {
    private HashMap<String, List<Observer>> observerMap;
    private List<Observer> observers;
    /**
     * Constructor to set up an empty Hash map.
     */
    public ObservableChatroom(){
        observerMap = new HashMap<>();
    }
    /**
     * Adds an observer to the list correspondning to a certain key.
     * @param key The key to map a type of observer
     * @param obs The observer that should be added
     * @throws NullPointerException - if the specified obs is null and this list does not permit null elements
     */
    public void addSubscriber(String key, Observer obs){
        observers = observerMap.get(key);
        if(observers == null){
            observers = new LinkedList<>();
        }
        observers.add(obs);
    }
    /**
     * Removes an observer from the list corresponding to a certain key.
     * @param key The key to map a type of observer
     * @param obs The observer that should be added
     * @throws NullPointerException - if the specified obs is null and this list does not permit null elements 
     */
    public void removeSubscriber(String key, Observer obs){
        observers = observerMap.get(key);
        if(observers != null){
            observers.remove(obs);
        }
    }
    /**
     * Notifies every observer, corresponding to a certain key, to update their internal state. 
     * @param key The key to map a type of observer
     * @param arg The information which the observer should use to update its internal state
     */
    public void notify(String key, Object arg){
        observers = observerMap.get(key);
        if(observers != null){
            for(Observer obs : observers){
                obs.update(arg);
            }
        }
    }
}
