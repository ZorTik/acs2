package me.zort.acs.domain.provider;

import me.zort.acs.api.domain.garbage.disposable.CacheDisposable;
import me.zort.acs.api.domain.provider.SubjectProvider;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.provider.options.SubjectOptions;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class SubjectProviderImpl implements SubjectProvider, CacheDisposable {

    @Cacheable(value = "subjects", key = "#options.subjectType.id + ':' + #options.id")
    public Subject getSubject(SubjectOptions options) {
        return new Subject(options.getSubjectType(), options.getId());
    }

    @Override
    public Set<String> getCacheKeys() {
        return Set.of("subjects");
    }
}
