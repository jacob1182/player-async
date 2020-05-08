package org.examples;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author jacob1182
 * */
public interface MessageListener extends Remote {

    /**
     * Invoked when a message is sent to the listener name
     *
     * @param from the message sender name
     * @param message the received message
     * */
    void messageHandler(String from, Message message) throws RemoteException;

    /**
     * Returns the message listener name
     *
     * @apiNote the name should be unique to every listener
     * */
    String getName() throws RemoteException;
}
