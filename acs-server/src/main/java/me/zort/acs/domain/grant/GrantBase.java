package me.zort.acs.domain.grant;

import lombok.Getter;
import me.zort.acs.api.domain.model.Grant;
import me.zort.acs.domain.model.Subject;

import java.util.UUID;

public abstract class GrantBase implements Grant {
    @Getter
    private final UUID id;
    private final Subject holder;
    private final Subject on;

    public GrantBase(UUID id, Subject holder, Subject on) {
        this.id = id;
        this.holder = holder;
        this.on = on;
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
