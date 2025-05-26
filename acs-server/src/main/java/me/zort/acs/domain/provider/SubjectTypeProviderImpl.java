package me.zort.acs.domain.provider;

import me.zort.acs.api.domain.provider.SubjectTypeProvider;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.domain.provider.options.SubjectTypeOptions;
import org.springframework.stereotype.Component;

@Component
public class SubjectTypeProviderImpl implements SubjectTypeProvider {

    @Override
    public SubjectType getSubjectType(SubjectTypeOptions options) {
        return new SubjectType(options.getId(), options.getNodes());
    }
}
