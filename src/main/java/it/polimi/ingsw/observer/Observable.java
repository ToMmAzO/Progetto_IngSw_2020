package it.polimi.ingsw.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to keep memory of all specific observers and notify them.
 *
 * @param <T> is a generic object that observers observe.
 */
public class Observable<T> {

    private final List<Observer<T>> observers = new ArrayList<>();

    /**
     * This method is used to add a new observer.
     *
     * @param observer is the entity of the new observer.
     */
    public void addObserver(Observer<T> observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    /**
     * This method notifies all observer in the list.
     *
     * @param message is the generic observed object.
     */
    protected void notify(T message){
        synchronized (observers) {
            for(Observer<T> observer : observers){
                observer.update(message);
            }
        }
    }

}
