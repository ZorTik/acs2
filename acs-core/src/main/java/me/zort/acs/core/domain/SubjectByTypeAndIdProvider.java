package me.zort.acs.core.domain;

import lombok.AllArgsConstructor;
import me.zort.acs.client.http.model.Subject;
import me.zort.acs.client.v1.AcsClientV1;

@AllArgsConstructor
public class SubjectByTypeAndIdProvider implements SubjectProvider {
    private final String subjectType;
    private final Object subjectId;

    @Override
    public Subject getSubject(AcsClientV1 client) {
        return Subject.of(subjectType, subjectId);
    }
}
