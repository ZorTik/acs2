package me.zort.acs.client.v1.model.check;

import lombok.Getter;
import me.zort.acs.client.AcsSubjectResolvable;
import me.zort.acs.client.http.model.AcsSubject;

import java.util.Set;

@Getter
public class CheckAccessRequestV1 {
    private final AcsSubject accessor;
    private final AcsSubject resource;
    private final Set<String> nodes;

    public CheckAccessRequestV1(AcsSubjectResolvable accessor, AcsSubjectResolvable accessed, Set<String> nodes) {
        this.accessor = AcsSubject.from(accessor);
        this.resource = AcsSubject.from(accessed);
        this.nodes = nodes;
    }

    public Set<String> getNodes() {
        return Set.copyOf(nodes);
    }
}
