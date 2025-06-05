package me.zort.acs.api.domain.grant;

import me.zort.acs.api.domain.access.RightsHolder;
import me.zort.acs.api.domain.model.Grant;
import me.zort.acs.data.entity.GrantEntity;
import me.zort.acs.data.id.SubjectId;
import me.zort.acs.domain.provider.options.GrantOptions;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class GrantAdapter implements GrantFactory, GrantRepositoryAdapter {

    protected abstract Grant doCreateGrant(GrantOptions options);

    protected abstract Optional<GrantEntity> doGetGrantEntity(
            SubjectId accessorId, SubjectId accessedId, RightsHolder rightsHolder);

    protected abstract Class<?>[] getSupportedGrantTypes();

    @Override
    public final @Nullable Grant createGrant(GrantOptions options) {
        requireSupportedType(options.getRightsHolder());

        return doCreateGrant(options);
    }

    @Override
    public final Optional<GrantEntity> getGrantEntity(SubjectId accessorId, SubjectId accessedId, RightsHolder rightsHolder) {
        requireSupportedType(rightsHolder);

        return doGetGrantEntity(accessorId, accessedId, rightsHolder);
    }

    public final void requireSupportedType(RightsHolder rightsHolder) {
        Class<? extends RightsHolder> classOfRightsHolder = rightsHolder.getClass();

        if (!ArrayUtils.contains(getSupportedGrantTypes(), classOfRightsHolder)) {
            throw new IllegalArgumentException("Invalid rights holder: " + classOfRightsHolder.getSimpleName());
        }
    }
}
