package me.zort.acs.domain.grant;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.api.domain.grant.GrantRepositoryAdapter;
import me.zort.acs.api.domain.grant.RightsHolderTypeRegistry;
import me.zort.acs.data.entity.GrantEntity;
import me.zort.acs.data.id.SubjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class GrantRepositoryAdapterImpl implements GrantRepositoryAdapter {
    private final RightsHolderTypeRegistry rightsHolderTypeRegistry;

    @Override
    public Optional<GrantEntity> getGrantEntity(SubjectId accessorId, SubjectId accessedId, RightsHolder rightsHolder) {
        return rightsHolderTypeRegistry.castAndCallAdapter(
                rightsHolder,
                (holder, type) ->
                        type.findGrantEntityForHolder(holder, accessorId, accessedId));
    }
}
