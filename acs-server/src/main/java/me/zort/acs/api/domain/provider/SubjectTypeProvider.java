package me.zort.acs.api.domain.provider;

import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.domain.provider.options.SubjectTypeOptions;

public interface SubjectTypeProvider {

    SubjectType getSubjectType(SubjectTypeOptions options);
}
