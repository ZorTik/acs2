package me.zort.acs.proto.serialization;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import me.zort.acs.proto.WrappedMessage;
import me.zort.acs.proto.exception.NotProtoMessageException;

@RequiredArgsConstructor
public class GsonSerializationStrategy implements ProtoSerializationStrategy<JsonElement> {
    private static final TypeToken<WrappedMessage<JsonElement>> WRAPPER_TOKEN = new TypeToken<>() {};

    private final Gson gson;

    @Override
    public String serializeWrapper(WrappedMessage<?> message) {
        return gson.toJson(message);
    }

    @Override
    public WrappedMessage<JsonElement> deserializeWrapper(String message) throws NotProtoMessageException {
        WrappedMessage<JsonElement> wrappedMessage = gson.fromJson(message, WRAPPER_TOKEN.getType());

        if (wrappedMessage == null || wrappedMessage.getKey() == null || wrappedMessage.getContent() == null) {
            throw new NotProtoMessageException();
        }

        return wrappedMessage;
    }

    @Override
    public Object deserializeContent(JsonElement content, Class<?> typeClass) {
        if (content == null || content.isJsonNull()) {
            return null;
        }

        return gson.fromJson(content, typeClass);
    }
}
