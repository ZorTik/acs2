package me.zort.acs.client.v1;

import me.zort.acs.client.AcsClient;
import me.zort.acs.client.AcsClientBuilder;

public class AcsClientBuilderV1 extends AcsClientBuilder {
    @Override
    protected AcsClient doBuild() {
        return new AcsClientV1(getBaseUrl(), getHttpAdapter(), getResponseMapper());
    }
}
