package me.zort.acs.proto.serialization;

import me.zort.acs.proto.WrappedMessage;
import me.zort.acs.proto.exception.NotProtoMessageException;

/**
 * Strategy interface for serialization and deserialization of protocol messages.
 * <p>
 * Defines methods for converting {@link WrappedMessage} objects to and from their
 * textual representation, as well as deserializing the message content to a specific type.
 * </p>
 *
 * @param <C> the type of the message content after deserialization
 */
public interface ProtoSerializationStrategy<C> {

    /**
     * Serializes the given message to its textual representation.
     *
     * @param message the message to serialize
     * @return the serialized textual representation of the message
     */
    String serializeWrapper(WrappedMessage<?> message);

    /**
     * Deserializes the textual representation of a message back to a {@link WrappedMessage}.
     *
     * @param message the serialized textual representation of the message
     * @return the deserialized message
     * @throws NotProtoMessageException if the message is not a valid protocol message
     */
    WrappedMessage<C> deserializeWrapper(String message) throws NotProtoMessageException;

    /**
     * Deserializes the message content to the specified type.
     * The content is specified in {@link WrappedMessage} and its type is the one that
     * was returned in {@link #deserializeWrapper(String)}.
     *
     * @param content the message content
     * @param typeClass the class of the desired type
     * @return the deserialized object of the specified type
     */
    Object deserializeContent(C content, Class<?> typeClass);
}
