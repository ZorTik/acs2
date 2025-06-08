package me.zort.acs.client;

import me.zort.acs.client.v1.AcsClientBuilderV1;
import org.jetbrains.annotations.NotNull;

public interface AcsClient {

    /**
     * Creates a new builder instance for ACS Client version 1.
     *
     * @return a builder for AcsClient v1
     */
    static @NotNull AcsClientBuilderV1 v1() {
        return new AcsClientBuilderV1();
    }
}
