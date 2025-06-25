package me.zort.acs.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zort.acs.api.messaging.ProtoAdapterRegistry;
import me.zort.acs.api.messaging.ProtoMessageSplitter;
import me.zort.acs.api.messaging.listener.ConvertedMessageListener;
import me.zort.acs.proto.ProtoAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class ProtoMessageSplitterImpl implements ProtoMessageSplitter {
    private final ProtoAdapterRegistry adapterRegistry;
    private final List<ConvertedMessageListener<?>> messageListeners;

    @SuppressWarnings("unchecked, rawtypes")
    @Override
    public void onMessage(@NotNull Message message, byte[] pattern) {
        String messageString = new String(message.getBody(), StandardCharsets.UTF_8);
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);

        ProtoAdapter adapter = adapterRegistry.getAdapter(channel);
        if (adapter == null) {
            log.debug("No adapter found for channel {}", channel);
            return;
        }

        Object messageObject = adapter.deserialize(messageString);
        if (messageObject == null) {
            log.warn("Received a message on channel {}, but it does not have a valid object representation.", channel);
            return;
        }

        for (ConvertedMessageListener<?> listener : messageListeners) {
            if (!listener.getChannel().equals(channel)) {
                continue;
            }

            if (!listener.getMessageType().isAssignableFrom(messageObject.getClass())) {
                continue;
            }

            try {
                ((ConvertedMessageListener) listener).onMessage(messageObject);
            } catch (Exception e) {
                log.error("Failed to handle incoming message on channel {}: {}", channel, e.getMessage(), e);
            }
        }
    }

    @Override
    public Collection<? extends Topic> getTopics() {
        return messageListeners
                .stream()
                .map(ConvertedMessageListener::getChannel).distinct()
                .map(ChannelTopic::new).toList();
    }
}
