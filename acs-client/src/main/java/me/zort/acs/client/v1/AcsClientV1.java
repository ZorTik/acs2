package me.zort.acs.client.v1;

import me.zort.acs.client.AbstractAcsClient;
import me.zort.acs.client.AcsNodeResolvable;
import me.zort.acs.client.AcsSubjectResolvable;
import me.zort.acs.client.http.HttpMethod;
import me.zort.acs.client.http.adapter.HttpAdapter;
import me.zort.acs.client.http.serializer.HttpSerializer;
import me.zort.acs.client.http.model.check.CheckAccessResponse;
import me.zort.acs.client.v1.model.check.CheckAccessRequestV1;
import me.zort.acs.client.v1.model.check.CheckAccessResponseV1;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Collectors;

public class AcsClientV1 extends AbstractAcsClient {
    private static final String PREFIX = "/v1";
    private static final String CHECK_ACCESS_URL = PREFIX + "/access/check";

    public AcsClientV1(String baseUrl, HttpAdapter httpAdapter, HttpSerializer httpSerializer) {
        super(baseUrl, httpAdapter, httpSerializer);
    }

    @Override
    public @NotNull CheckAccessResponse checkAccess(
            final @NotNull AcsSubjectResolvable accessor,
            final @NotNull AcsSubjectResolvable accessed, final @NotNull Set<AcsNodeResolvable> nodes) {
        Set<String> values = nodes
                .stream()
                .map(AcsNodeResolvable::getValue).collect(Collectors.toSet());

        return makeCall(CheckAccessResponseV1.class, (builder, serializer) -> builder
                .method(HttpMethod.POST)
                .path(CHECK_ACCESS_URL)
                .body(serializer.apply(new CheckAccessRequestV1(accessor, accessed, values))));
    }
}
