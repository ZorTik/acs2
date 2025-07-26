package me.zort.acs.domain.grant;

import lombok.Getter;
import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.api.domain.model.Grant;
import me.zort.acs.domain.model.Subject;

import java.util.UUID;

public class GrantImpl implements Grant {
    @Getter
    private final UUID id;
    private final Subject holder;
    private final Subject on;
    @Getter
    private final RightsHolder rightsHolder;

    public GrantImpl(UUID id, Subject holder, Subject on, RightsHolder rightsHolder) {
        this.id = id;
        this.holder = holder;
        this.on = on;
        this.rightsHolder = rightsHolder;
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
}
