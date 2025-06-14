package me.zort.acs.proto;

import me.zort.acs.proto.exception.MessageTypeNotDefinedException;
import me.zort.acs.proto.exception.NotProtoMessageException;
import me.zort.acs.proto.serialization.ProtoSerializationStrategy;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Optional;

/**
 * Adapter class for serializing and deserializing protocol messages.
 * <p>
 * Handles mapping between message types and their serialization strategies.
 * </p>
 */
public class ProtoAdapter {
    private final Collection<? extends MessageTypeEntry> types;
    private final ProtoSerializationStrategy<?> serializationStrategy;

    /**
     * Constructs a new ProtoAdapter with the given message type entries and serialization strategy.
     *
     * @param types the collection of message type entries supported by this adapter
     * @param serializationStrategy the strategy used for (de)serialization of messages
     */
    public ProtoAdapter(
            Collection<? extends MessageTypeEntry> types, ProtoSerializationStrategy<?> serializationStrategy) {
        this.types = types;
        this.serializationStrategy = serializationStrategy;
    }

    private Optional<? extends MessageTypeEntry> typeEntryForClass(Class<?> clazz) {
        return types
                .stream()
                .filter(entry -> entry.getMessageClass().isAssignableFrom(clazz))
                .findFirst();
    }

    /**
     * Serializes the given data object into a protocol message string.
     *
     * @param data the object to serialize
     * @return the serialized protocol message as a string
     * @throws MessageTypeNotDefinedException if the object's type is not registered in the adapter
     */
    public String serialize(Object data) throws MessageTypeNotDefinedException {
        MessageTypeEntry typeEntry = typeEntryForClass(data.getClass())
                .orElseThrow(() -> new MessageTypeNotDefinedException(data.getClass()));

        WrappedMessage<?> wrappedMessage = new WrappedMessage<>(typeEntry.getKey(), data);

        return serializationStrategy.serializeWrapper(wrappedMessage);
    }

    private Optional<? extends MessageTypeEntry> typeEntryForKey(String key) {
        return types
                .stream()
                .filter(entry -> entry.getKey().equals(key))
                .findFirst();
    }

    /**
     * Deserializes the given protocol message string into an object.
     *
     * @param message the protocol message string to deserialize
     * @param <C> the type of the deserialized object
     * @return the deserialized object, or {@code null} if the message is not a valid protocol message
     */
    @Nullable
    public <C> Object deserialize(String message) {
        @SuppressWarnings("unchecked") // Type safety is ensured within the interface architecture
        ProtoSerializationStrategy<C> deserializationStrategy = (ProtoSerializationStrategy<C>) serializationStrategy;

        WrappedMessage<C> wrappedMessage;
        try {
            wrappedMessage = deserializationStrategy.deserializeWrapper(message);
        } catch (NotProtoMessageException e) {
            // This is not a proto message
            return null;
        }

        return typeEntryForKey(wrappedMessage.getKey())
                .map(entry ->
                        deserializeWrappedMessageContent(entry, deserializationStrategy, wrappedMessage))
                .orElse(null);
    }

    private static <C> Object deserializeWrappedMessageContent(
            MessageTypeEntry entry,
            ProtoSerializationStrategy<C> deserializationStrategy, WrappedMessage<C> wrappedMessage) {
        try {
            return deserializationStrategy.deserializeContent(wrappedMessage.getContent(), entry.getMessageClass());
        } catch (Exception e) {
            // If deserialization fails, we return null
            // This is to ensure that the deserialization process is robust against unexpected content
            return null;
        }
    }
}
