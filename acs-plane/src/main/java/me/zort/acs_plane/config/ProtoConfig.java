package me.zort.acs_plane.config;

import com.google.gson.Gson;
import me.zort.acs.proto.MessageTypeEntries;
import me.zort.acs.proto.ProtoAdapter;
import me.zort.acs.proto.plane.PlaneMessage;
import me.zort.acs.proto.serialization.GsonSerializationStrategy;
import me.zort.acs.proto.serialization.ProtoSerializationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;

@Configuration
public class ProtoConfig {

    @Bean
    public ProtoSerializationStrategy<?> protoSerializationStrategy(Gson gson) {
        return new GsonSerializationStrategy(gson);
    }

    @Bean(name = "planeMessageDefinitions")
    public ProtoAdapter protoAdapter(ProtoSerializationStrategy<?> serializationStrategy) {
        Collection<PlaneMessage> messageDefinitions = MessageTypeEntries.fromImplementingEnum(PlaneMessage.class);

        return new ProtoAdapter(messageDefinitions, serializationStrategy);
    }
}
