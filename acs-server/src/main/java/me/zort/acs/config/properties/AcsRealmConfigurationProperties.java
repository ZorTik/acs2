package me.zort.acs.config.properties;

import lombok.Data;
import me.zort.acs.proto.plane.PlaneProtocol;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "acs.realm")
@Component
public class AcsRealmConfigurationProperties {
    private String name;

    public String getChannelNameForPlaneProto() {
        return PlaneProtocol.getChannelForRealm(name);
    }
}
