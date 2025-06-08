package me.zort.acs.client.v1;

import me.zort.acs.client.AcsClientBuilder;

public class AcsClientBuilderV1 extends AcsClientBuilder<AcsClientV1> {
    @Override
    protected AcsClientV1 doBuild() {
        return new AcsClientV1(getBaseUrl(), getHttpAdapter(), getResponseMapper());
    }
}
