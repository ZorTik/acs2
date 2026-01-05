package me.zort.acs.api.domain.model;

import me.zort.acs.core.domain.access.rights.RightsHolder;
import me.zort.acs.core.model.Subject;

import java.util.UUID;

public interface Grant {

    UUID getId();

    Subject getAccessor();

    Subject getAccessed();

    boolean isValid();

    RightsHolder getRightsHolder();
}
