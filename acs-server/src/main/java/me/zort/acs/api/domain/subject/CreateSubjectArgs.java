package me.zort.acs.api.domain.subject;

import lombok.Builder;
import lombok.Getter;
import me.zort.acs.domain.model.SubjectType;

@Getter
@Builder
public class CreateSubjectArgs {
    private final SubjectType subjectType;
    private final String id;

}
