package me.zort.acs.domain.provider.options;

import lombok.Builder;
import lombok.Getter;
import me.zort.acs.core.model.SubjectType;

@Builder
@Getter
public class SubjectOptions {
    private final SubjectType subjectType;
    private final String id;

}
