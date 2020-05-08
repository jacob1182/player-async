package org.examples;

import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author jacob1182
 * */
class MessageSenderImplTest {

    @Test
    void sendSuccessfully() throws RemoteException {
        var sender = new MessageSenderImpl();
        var listener1 = getMessageListener();
        var listener2 = getMessageListener();

        sender.registerListeners(listener1, listener2);

        var message = new Message("Hi!!");
        sender.send(listener1.getName(), listener2.getName(), message);

        verify(listener2).messageHandler(listener1.getName(), message);
    }

    @Test
    void sendFailure() throws RemoteException {
        var sender = new MessageSenderImpl();
        var message = new Message("Hi!!");

        assertThrows(IllegalArgumentException.class,
                () -> sender.send("non-existent", "non-existent", message));
    }

    private MessageListener getMessageListener() throws RemoteException {
        var listener = mock(MessageListener.class);
        when(listener.getName()).thenReturn(UUID.randomUUID().toString());
        return listener;
    }
}