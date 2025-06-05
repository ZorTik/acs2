package me.zort.acs.api.domain.grant;

import me.zort.acs.api.domain.access.RightsHolder;
import me.zort.acs.api.domain.access.RightsHolderPresenceVerifier;
import org.apache.commons.lang3.ArrayUtils;

public abstract class RightsAdapter implements GrantFactory, GrantRepositoryAdapter, RightsHolderPresenceVerifier {

    protected abstract Class<?>[] getSupportedHolderTypes();

    public final void requireSupportedType(RightsHolder rightsHolder) {
        Class<? extends RightsHolder> classOfRightsHolder = rightsHolder.getClass();

        if (!ArrayUtils.contains(getSupportedHolderTypes(), classOfRightsHolder)) {
            throw new IllegalArgumentException("Invalid rights holder: " + classOfRightsHolder.getSimpleName());
        }
    }
}
