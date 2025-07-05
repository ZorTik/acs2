package me.zort.acs.api.messaging;

import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.Topic;

import java.util.Collection;

public interface ProtoMessageSplitter extends MessageListener {

    Collection<? extends Topic> getTopics();
}
