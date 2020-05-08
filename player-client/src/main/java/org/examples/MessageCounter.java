package org.examples;

import java.util.HashMap;
import java.util.Map;

/**
 * Auxiliary class to manage message counters.
 *
 * @apiNote
 * Each player has its own MessageCounter, and can not be shared between players
 * @author jacob1182
 **/
public class MessageCounter {

    private final Map<String, Integer> messageCounters = new HashMap<>();
    private final int resendLimit;

    public MessageCounter(int resendLimit) {
        this.resendLimit = resendLimit;
    }

    /**
     * Verify if a message reached the resend limit
     *
     * @param message the message to verify
     * */
    public boolean isMessageResendLimitReached(Message message) {
        return getMessageCount(message) == resendLimit;
    }

    /**
     * Perform the message counting to the provided message
     *
     * @param message the message to evaluate
     * @return the message count after the increment.
     * or the resend limit value if it was reached already
     * */
    public int incrementMessageCount(Message message) {
        if (isMessageResendLimitReached(message))
            return resendLimit;

        String plainPayload = message.getPlainPayload();
        messageCounters.put(plainPayload, getMessageCount(message) + 1);

        return messageCounters.get(plainPayload);
    }

    /**
     * @return the message count of the provided message
     * */
    public int getMessageCount(Message message) {
        String plainPayload = message.getPlainPayload();
        return messageCounters.getOrDefault(plainPayload, 0);
    }
}
