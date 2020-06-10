package it.polimi.ingsw.network.client;

import java.io.IOException;

public interface Client<T> {

    Thread asyncReadFromSocket();

    Thread asyncWriteToSocket(T object);

    void run() throws IOException;

}
