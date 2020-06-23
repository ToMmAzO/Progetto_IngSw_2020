package it.polimi.ingsw.observer;

/**
 * This class represents the Observer pattern
 *
 * @param <T> is the generic observed object.
 */
public interface Observer<T> {

    /**
     * This method notifies this specific observer of change in observed object.
     *
     * @param message is the observed object by this observer.
     */
    void update(T message);

}
