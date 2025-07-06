package me.zort.acs.domain.access;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.*;
import me.zort.acs.api.domain.access.request.AccessRequest;
import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.api.domain.subject.SubjectLike;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class AccessServiceImpl implements AccessService {
    private final AccessControlService accessControlService;
    private final AccessRequestFactory accessRequestFactory;
    private final AccessQueryService accessQueryService;

    @Override
    public Page<? extends SubjectLike> getAccessibleSubjects(
            SubjectLike accessor, SubjectType targetSubjectType, List<RightsHolder> rightsHolders, Pageable pageable) {
        return accessQueryService.performAggregatedQuery((source, req) ->
                source.queryForAccessibleSubjects(accessor, targetSubjectType, rightsHolders, req), pageable);
    }

    @Override
    public Map<Node, Boolean> getGrantStatesBetween(SubjectLike accessor, SubjectLike accessed) {
        return accessed.getSubjectType().getNodes()
                .stream()
                .collect(Collectors.toMap(Function.identity(), node -> {
                    AccessRequest request = accessRequestFactory.createAccessRequest(accessor, accessed, node);
                    accessControlService.checkAccess(request);

                    return request.isGranted();
                }));
    }

    @Override
    public boolean hasAccess(SubjectLike accessor, SubjectLike accessed, RightsHolder rightsHolder) {
        AccessRequest request = accessRequestFactory.createAccessRequest(accessor, accessed, rightsHolder);

        accessControlService.checkAccess(request);

        return request.isGranted();
    }
}
