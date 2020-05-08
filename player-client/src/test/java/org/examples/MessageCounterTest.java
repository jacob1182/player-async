package org.examples;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jacob1182
 * */
class MessageCounterTest {

    @Test
    void incrementMessageCount() {
        var message = new Message("Hi!!");
        var messageCounter = new MessageCounter(2);
        assertEquals(1, messageCounter.incrementMessageCount(message));
        assertEquals(2, messageCounter.incrementMessageCount(message.withMessageCount(2)));
        assertEquals(2, messageCounter.incrementMessageCount(message.withMessageCount(3)));
    }

    @Test
    void whenMessageResendLimitIsNotReached() {
        var message = new Message("Hi!!");
        var messageCounter = new MessageCounter(2);
        assertFalse(messageCounter.isMessageResendLimitReached(message));
    }

    @Test
    void whenMessageResendLimitIsReached() {
        var message = new Message("Hi!!");
        var messageCounter = new MessageCounter(2);
        messageCounter.incrementMessageCount(message);
        messageCounter.incrementMessageCount(message.withMessageCount(2));
        assertTrue(messageCounter.isMessageResendLimitReached(message));
    }
}