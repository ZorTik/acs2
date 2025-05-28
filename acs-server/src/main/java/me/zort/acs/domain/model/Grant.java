package me.zort.acs.domain.model;

import lombok.Getter;
import me.zort.acs.api.domain.access.RightsHolder;

import java.util.Set;

public class Grant implements RightsHolder {
    private final Subject holder;
    private final Subject on;
    @Getter
    private final Node node;

    public Grant(Subject holder, Subject on, Node node) {
        this.holder = holder;
        this.on = on;
        this.node = node;
    }

    public Subject getAccessor() {
        return holder;
    }

    public Subject getAccessed() {
        return on;
    }

    /**
     * Returns if this grant is now ready to be checked for the applicable state.
     * Extending classes should override this and set their own logic for validity.
     *
     * @return The valid state of this grant
     */
    public boolean isValid() {
        return true;
    }

    @Override
    public Set<Node> getGrantedNodes() {
        return Set.of(node);
    }
}
