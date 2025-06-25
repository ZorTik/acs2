package me.zort.acs.config;

import com.google.gson.Gson;
import me.zort.acs.api.messaging.ProtoMessageSplitter;
import me.zort.acs.proto.serialization.GsonSerializationStrategy;
import me.zort.acs.proto.serialization.ProtoSerializationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class RedisConfig {

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisConnectionFactory connectionFactory, ProtoMessageSplitter messageSplitter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setTopicSerializer(new StringRedisSerializer());

        messageSplitter.getTopics()
                .forEach(topic -> container.addMessageListener(messageSplitter, topic));
        return container;
    }

    @Bean
    public ProtoSerializationStrategy<?> serializationStrategy() {
        return new GsonSerializationStrategy(new Gson());
    }
}
