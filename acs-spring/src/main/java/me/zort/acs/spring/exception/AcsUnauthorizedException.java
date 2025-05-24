package me.zort.acs.spring.exception;

import lombok.Getter;
import me.zort.acs.client.AcsNodeResolvable;
import me.zort.acs.client.AcsSubjectResolvable;

import java.util.Set;

@Getter
public class AcsUnauthorizedException extends RuntimeException {
    private final AcsSubjectResolvable accessor;
    private final AcsSubjectResolvable accessed;
    private final Set<AcsNodeResolvable> nodes;

    public AcsUnauthorizedException(
            AcsSubjectResolvable accessor, AcsSubjectResolvable accessed, Set<AcsNodeResolvable> nodes) {
        this.accessor = accessor;
        this.accessed = accessed;
        this.nodes = Set.copyOf(nodes);
    }
}
