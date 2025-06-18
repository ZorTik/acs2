package me.zort.acs.domain.access;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.AccessControlService;
import me.zort.acs.api.domain.access.AccessQueryService;
import me.zort.acs.api.domain.access.AccessRequestFactory;
import me.zort.acs.api.domain.access.AccessService;
import me.zort.acs.api.domain.access.request.AccessRequest;
import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.api.domain.model.SubjectLike;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class AccessServiceImpl implements AccessService {
    private final AccessControlService accessControlService;
    private final AccessRequestFactory accessRequestFactory;
    private final AccessQueryService accessQueryService;

    @Override
    public Page<Subject> getAccessibleSubjects(
            SubjectLike accessor, SubjectType targetSubjectType, List<RightsHolder> rightsHolders, Pageable pageable) {
        return accessQueryService.performAggregatedQuery((source, req) -> source
                .queryForAccessibleSubjects(accessor, targetSubjectType, rightsHolders, req), pageable);
    }

    @Override
    public boolean hasAccess(SubjectLike accessor, SubjectLike accessed, RightsHolder rightsHolder) {
        AccessRequest request = accessRequestFactory.createAccessRequest(accessor, accessed, rightsHolder);

        accessControlService.checkAccess(request);

        return request.isGranted();
    }
}
