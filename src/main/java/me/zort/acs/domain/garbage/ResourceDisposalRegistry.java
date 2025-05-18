package me.zort.acs.domain.garbage;

import me.zort.acs.domain.garbage.disposal.ResourceDisposal;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceDisposalRegistry {
    private final List<ResourceDisposal<?>> disposals;

    @Autowired
    public ResourceDisposalRegistry(List<ResourceDisposal<?>> disposals) {
        this.disposals = disposals;
    }

    // Type safety is ensured.
    @SuppressWarnings("unchecked")
    public @NotNull <T> ResourceDisposal<T> getDisposalFor(T resource) {
        return disposals
                .stream()
                .filter(disposal -> disposal.getResourceType().isAssignableFrom(resource.getClass()))
                .map(disposal -> (ResourceDisposal<T>) disposal)
                .findFirst().orElseThrow(() -> new IllegalArgumentException(
                        "No disposal found for resource of type " + resource.getClass().getName()));
    }
}
