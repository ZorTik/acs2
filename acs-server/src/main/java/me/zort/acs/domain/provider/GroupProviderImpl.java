package me.zort.acs.domain.provider;

import me.zort.acs.api.domain.garbage.disposable.CacheDisposable;
import me.zort.acs.api.domain.provider.GroupProvider;
import me.zort.acs.domain.model.Group;
import me.zort.acs.domain.provider.options.GroupOptions;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class GroupProviderImpl implements GroupProvider, CacheDisposable {

    @Cacheable(value = "groups", key = "#options.subjectType.id + ':' + #options.name")
    @Override
    public Group getGroup(GroupOptions options) {
        return new Group(options.getSubjectType(), options.getName(), options.getParentGroup(), options.getNodes());
    }

    @Override
    public Set<String> getCacheKeys() {
        return Set.of("groups");
    }
}