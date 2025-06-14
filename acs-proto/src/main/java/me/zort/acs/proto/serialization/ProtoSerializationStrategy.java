package me.zort.acs.proto.serialization;

import me.zort.acs.proto.WrappedMessage;
import me.zort.acs.proto.exception.NotProtoMessageException;

public interface ProtoSerializationStrategy<C> {

    String serializeWrapper(WrappedMessage<?> message);

    WrappedMessage<C> deserializeWrapper(String message) throws NotProtoMessageException;

    Object deserializeContent(C content, Class<?> typeClass);
}
