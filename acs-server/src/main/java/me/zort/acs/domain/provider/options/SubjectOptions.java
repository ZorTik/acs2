package me.zort.acs.domain.provider.options;

import lombok.Builder;
import lombok.Getter;
import me.zort.acs.domain.model.Group;
import me.zort.acs.domain.model.SubjectType;

import java.util.List;

@Builder
@Getter
public class SubjectOptions {
    private final SubjectType subjectType;
    private final String id;

}
