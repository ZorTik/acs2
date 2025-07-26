package me.zort.acs.api.domain.provider;

import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.domain.provider.options.SubjectTypeOptions;

public interface SubjectTypeProvider {

    /**
     * Returns the subject type based on the provided options.
     *
     * @param options the options to determine the subject type
     * @return the determined subject type
     */
    SubjectType getSubjectType(SubjectTypeOptions options);
}
