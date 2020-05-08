package org.examples;

import java.io.Serializable;

import static java.lang.String.format;

/**
 * Represents a message.
 *
 * @implNote
 * Immutability is enforced for thread-safety and
 * avoid race conditions when multiple tenants hold the same instance
 * @author jacob1182
 * */
public class Message implements Serializable {
    private static final String COUNTER_SEPARATOR = " : ";
    private final String payload;

    /**
     * Message constructor
     *
     * @param payload the message payload
     * */
    public Message(String payload) {
        this.payload = payload;
    }

    /**
     * @return the message payload
     * */
    public String getPayload() {
        return payload;
    }

    /**
     * @return the payload without the counter description
     * */
    public String getPlainPayload() {
        return payload.split(COUNTER_SEPARATOR)[0];
    }

    /**
     * Updates the counter description in the payload.
     *
     * @param count the new counter description
     * @return a message instance with the counter description updated
     * @implNote A new message instance is returned to assure immutability
     * */
    public Message withMessageCount(int count) {
        return new Message(getFormattedPayload(count));
    }

    private String getFormattedPayload(int count) {
        return format("%s%s%d", getPlainPayload(), COUNTER_SEPARATOR, count);
    }
}