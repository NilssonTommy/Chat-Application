import java.util.*;

public class Observable {
    private List<Observer> observers;
    public Observable(){
        observers = new LinkedList<Observer>();
    }
    public void addSubscriber(Observer obs){
        observers.add(obs);
    }
    public void removeSubscriber(Observer obs){
        observers.remove(obs);
    }
    public void notify(Object arg){
        for(Observer obs : observers){
            obs.update(arg);
        }
    }
}