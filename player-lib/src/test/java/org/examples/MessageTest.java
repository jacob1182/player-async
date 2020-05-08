package org.examples;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jacob1182
 * */
class MessageTest {

    static final String COUNTER_SEPARATOR = " : ";

    @Test
    void getPayload() {
        var payload = "Hi!!!" + COUNTER_SEPARATOR + 1;
        var message = new Message(payload);
        assertEquals(payload, message.getPayload());
    }

    @Test
    void getPlainPayload() {
        var plainPayload = "Hi!!!";
        var plainMessage = new Message(plainPayload);
        assertEquals(plainPayload, plainMessage.getPayload());

        var messageWithCounter = new Message(plainPayload + COUNTER_SEPARATOR + 1);
        assertEquals(plainPayload, messageWithCounter.getPlainPayload());
    }

    @Test
    void withMessageCount() {
        var payload = "Hi!!!";
        var message = new Message(payload + COUNTER_SEPARATOR + 1);
        var updatedMessage = message.withMessageCount(2);

        assertNotEquals(message, updatedMessage);
        assertEquals(message.getPlainPayload(), updatedMessage.getPlainPayload());
        assertEquals(payload + COUNTER_SEPARATOR + 2, updatedMessage.getPayload());
    }
}