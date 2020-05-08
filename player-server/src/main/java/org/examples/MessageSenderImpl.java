package org.examples;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

/**
 * Mediator class to handle message communication
 * @author jacob1182
 * */
public class MessageSenderImpl implements MessageSender {

    private final Map<String, MessageListener> listeners = new HashMap<>();

    protected MessageSenderImpl() throws RemoteException { }

    /**
     * Register message listener
     *
     * @param listeners a message listener array
     * */
    public void registerListeners(MessageListener... listeners) throws RemoteException  {
        for (MessageListener listener: listeners) {
            this.listeners.put(listener.getName(), listener);
            System.out.println(listener.getName() + " is registered");
        }
    }

    /**
     * Perform sending a message to a specific destination
     *
     * @param from the sender name
     * @param to the receiver name
     * @param message the message to send
     * */
    public void send(String from, String to, Message message) throws RemoteException {
        if (listeners.containsKey(to)) {
            MessageListener listener = listeners.get(to);
            listener.messageHandler(from, message);
        } else
            throw new IllegalArgumentException("Unknown receiver: " + to);
    }
}
