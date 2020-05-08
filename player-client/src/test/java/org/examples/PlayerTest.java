package org.examples;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

/**
 * @author jacob1182
 * */
class PlayerTest {

    private MessageCounter messageCounter;
    private MessageSender sender;

    @BeforeEach
    void setup() {
        sender = mock(MessageSender.class);
    }

    @Test
    void getName() throws RemoteException {
        var playerName = "player1";
        var player = new Player(playerName, sender, messageCounter);
        assertEquals(playerName, player.getName());
    }

    @Test
    void sendMessage() throws RemoteException {
        messageCounter = genMessageCounterLimitNotReached();
        var message = new Message("Hi!!");
        var player = new Player("player1", sender, messageCounter);

        player.sendMessage("player2", message);

        var messageArgumentCaptor = ArgumentCaptor.forClass(Message.class);
        verify(sender).send(anyString(), anyString(), messageArgumentCaptor.capture());
        verify(messageCounter).incrementMessageCount(message);

        var sentMessage = messageArgumentCaptor.getValue();
        assertEquals(message.getPlainPayload(), sentMessage.getPlainPayload());
    }

    @Test
    void messageHandlerWhenResendLimitIsNotReachedShouldSendMessageBack() throws RemoteException {
        messageCounter = genMessageCounterLimitNotReached();
        var message = new Message("Hi!!").withMessageCount(1);
        var player = new Player("player2", sender, messageCounter);

        player.messageHandler("player1", message);

        verify(sender).send(anyString(), anyString(), any(Message.class));
        verify(messageCounter).incrementMessageCount(message);
        assertEquals(1, player.getInbox().size());
    }

    @Test
    void messageHandlerWhenResendLimitIsReachedShouldNotSendMessageBack() throws RemoteException {
        messageCounter = genMessageCounterLimitIsReached();
        var message = new Message("Hi!!").withMessageCount(1);
        var player = new Player("player2", sender, messageCounter);

        player.messageHandler("player1", message);

        verify(sender, times(0)).send(anyString(), anyString(), any(Message.class));
        verify(messageCounter, times(0)).incrementMessageCount(message);
        assertEquals(1, player.getInbox().size());
    }

    private MessageCounter genMessageCounterLimitNotReached() {
        return genMessageCounter(false);
    }

    private MessageCounter genMessageCounterLimitIsReached() {
        return genMessageCounter(true);
    }

    private MessageCounter genMessageCounter(boolean reachedLimit) {
        var messageCounter = mock(MessageCounter.class);
        when(messageCounter.incrementMessageCount(any(Message.class))).thenReturn(1);
        when(messageCounter.isMessageResendLimitReached(any(Message.class))).thenReturn(reachedLimit);
        return messageCounter;
    }
}