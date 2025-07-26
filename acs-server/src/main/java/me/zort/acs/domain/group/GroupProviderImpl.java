package me.zort.acs.domain.group;

import me.zort.acs.api.domain.garbage.disposable.CacheDisposable;
import me.zort.acs.api.domain.group.Group;
import me.zort.acs.api.domain.provider.GroupProvider;
import me.zort.acs.domain.provider.options.GroupOptions;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class GroupProviderImpl implements GroupProvider, CacheDisposable {

    @Cacheable(value = "groups", key = "#options.subjectType.id + ':' + #options.name + ':' + (#options.subject.id ?: '')")
    @Override
    public Group getGroup(GroupOptions options) {
        if (options.getSubjectType() == null && options.getSubject() == null) {
            throw new IllegalArgumentException("Either subjectType or subject must be provided.");
        }

        if (options.getSubject() != null) {
            return new DynamicGroup(options.getSubject(), options.getName(), options.getNodes(), options.getParentGroup());
        } else {
            return new StaticGroup(options.getSubjectType(), options.getName(), options.getNodes(), options.getParentGroup());
        }
    }

    @Override
    public Set<String> getCacheKeys() {
        return Set.of("groups");
    }
}