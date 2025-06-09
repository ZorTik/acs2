package me.zort.acs.api.domain.grant;

import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.api.domain.access.rights.RightsHolderPresenceVerifier;

import java.util.Set;

public abstract class RightsHolderAdapter implements GrantFactory, GrantRepositoryAdapter, RightsHolderPresenceVerifier {

    protected abstract Set<Class<?>> getSupportedHolderTypes();

    public final void requireSupportedType(RightsHolder rightsHolder) {
        Class<? extends RightsHolder> classOfRightsHolder = rightsHolder.getClass();

        if (!getSupportedHolderTypes().contains(classOfRightsHolder)) {
            throw new IllegalArgumentException("Invalid rights holder: " + classOfRightsHolder.getSimpleName());
        }
    }
}
