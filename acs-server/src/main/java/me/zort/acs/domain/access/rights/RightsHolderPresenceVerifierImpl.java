package me.zort.acs.domain.access.rights;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.api.domain.access.rights.RightsHolderPresenceVerifier;
import me.zort.acs.api.domain.grant.RightsHolderTypeRegistry;
import me.zort.acs.domain.model.SubjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class RightsHolderPresenceVerifierImpl implements RightsHolderPresenceVerifier {
    private final RightsHolderTypeRegistry rightsHolderTypeRegistry;

    @Override
    public boolean isPresentInSubjectType(SubjectType subjectType, RightsHolder rightsHolder) {
        return rightsHolderTypeRegistry.castAndCallAdapter(
                rightsHolder,
                (holder, type) ->
                        type.isPresentInSubjectType(holder, subjectType));
    }
}
