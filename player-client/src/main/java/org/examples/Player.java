package org.examples;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

/**
 * Represents a player in the system
 * @author jacob1182
 * */
public class Player extends UnicastRemoteObject implements MessageListener {

    // Note: YAGNI applied
    // The inbox logic can be extracted as a separated class if new requirements demand it.
    private final List<String> inbox = new ArrayList<>();

    private final MessageCounter messageCounter;
    private final MessageSender sender;
    private final String name;

    /**
     * Player constructor
     *
     * @param name the name of the player
     * @param sender the message sender mediator
     * */
    public Player(String name, MessageSender sender, MessageCounter messageCounter) throws RemoteException {
        this.name = name;
        this.sender = sender;
        this.messageCounter = messageCounter;
        sender.registerListeners(this);
    }

    /**
     * Send a message to a specific player
     *
     * @param to the receiver player name
     * @param message the message to be sent
     * */
    public void sendMessage(String to, Message message) throws RemoteException {
        int count = messageCounter.incrementMessageCount(message);
        sender.send(name, to, message.withMessageCount(count));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void messageHandler(String from, Message message) throws RemoteException {
        inbox.add(format("Received form %s: %s", from, message.getPayload()));

        if (!messageCounter.isMessageResendLimitReached(message))
            sendMessage(from, message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns all received messages
     * */
    public List<String> getInbox() {
        return inbox;
    }
}
