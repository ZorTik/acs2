package me.zort.acs.config;

import com.google.gson.Gson;
import me.zort.acs.api.messaging.ProtoAdapterRegistry;
import me.zort.acs.api.messaging.ProtoMessageSplitter;
import me.zort.acs.config.properties.AcsRealmConfigurationProperties;
import me.zort.acs.proto.MessageTypeEntries;
import me.zort.acs.proto.ProtoAdapter;
import me.zort.acs.proto.plane.PlaneProtocol;
import me.zort.acs.proto.serialization.GsonSerializationStrategy;
import me.zort.acs.proto.serialization.ProtoSerializationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new JedisConnectionFactory();
    }

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

    @Bean
    public void registerPlaneProtocol(
            ProtoAdapterRegistry adapterRegistry,
            ProtoSerializationStrategy<?> serializationStrategy, AcsRealmConfigurationProperties realmConfig) {
        ProtoAdapter adapter = new ProtoAdapter(
                MessageTypeEntries.fromImplementingEnum(PlaneProtocol.getImplementingEnum()), serializationStrategy);

        adapterRegistry.registerAdapter(realmConfig.getChannelNameForPlaneProto(), adapter);
    }
}
