package org.examples;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author jacob1182
 * */
public interface MessageSender extends Remote {

    /**
     * Register message listeners
     *
     * @param listeners a message listener array
     * */
    void registerListeners(MessageListener... listeners) throws RemoteException;

    /**
     * Perform sending a message to a specific destination
     *
     * @param from the sender name
     * @param to the receiver name
     * @param message the message to send
     * */
    void send(String from, String to, Message message) throws RemoteException;
}
