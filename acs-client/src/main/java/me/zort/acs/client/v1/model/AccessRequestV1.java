package me.zort.acs.client.v1.model;

import lombok.Getter;
import me.zort.acs.client.AcsSubjectResolvable;
import me.zort.acs.client.http.model.AcsSubject;

import java.util.Set;

@Getter
public class AccessRequestV1 {
    private final AcsSubject accessor;
    private final AcsSubject resource;
    private final Set<String> nodes;

    public AccessRequestV1(AcsSubjectResolvable accessor, AcsSubjectResolvable accessed, Set<String> nodes) {
        this.accessor = AcsSubject.from(accessor);
        this.resource = AcsSubject.from(accessed);
        this.nodes = nodes;
    }

    public Set<String> getNodes() {
        return Set.copyOf(nodes);
    }
}
