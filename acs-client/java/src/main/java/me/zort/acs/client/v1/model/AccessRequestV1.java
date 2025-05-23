package me.zort.acs.client.v1.model;

import lombok.Getter;
import me.zort.acs.client.AcsSubjectResolvable;
import me.zort.acs.client.http.model.AcsSubject;

@Getter
public class AccessRequestV1 {
    private final AcsSubject accessor;
    private final AcsSubject resource;

    public AccessRequestV1(AcsSubjectResolvable accessor, AcsSubjectResolvable accessed) {
        this.accessor = AcsSubject.from(accessor);
        this.resource = AcsSubject.from(accessed);
    }
}
