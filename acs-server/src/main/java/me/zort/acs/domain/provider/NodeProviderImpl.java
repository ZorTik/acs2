package me.zort.acs.domain.provider;

import me.zort.acs.api.domain.garbage.disposable.CacheDisposable;
import me.zort.acs.api.domain.provider.NodeProvider;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.provider.options.NodeOptions;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class NodeProviderImpl implements NodeProvider, CacheDisposable {

    @Cacheable(value = "nodes", key = "#options.value")
    @Override
    public Node getNode(NodeOptions options) {
        return new Node(options.getValue());
    }

    @Override
    public String getCacheKey() {
        return "nodes";
    }
}
