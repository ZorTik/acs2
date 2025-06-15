package me.zort.acs.proto;

/**
 * Proto definitions interface, to be implemented by enums that define message types.
 */
public interface MessageTypeEntry {

    /**
     * Returns the unique key identifying the message type.
     *
     * @return the message type key as a String
     */
    String getKey();

    /**
     * Returns the class representing the message type.
     *
     * @return the Class object of the message type
     */
    Class<?> getMessageClass();
}
