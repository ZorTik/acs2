package me.zort.acs.client.v1.model;

import lombok.Getter;
import me.zort.acs.client.AcsSubjectResolvable;
import me.zort.acs.client.http.model.Subject;

@Getter
public class AccessRequestV1 {
    private final Subject accessor;
    private final Subject resource;

    public AccessRequestV1(AcsSubjectResolvable accessor, AcsSubjectResolvable accessed) {
        this.accessor = Subject.from(accessor);
        this.resource = Subject.from(accessed);
    }
}
