package me.zort.acs.domain.access;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.RightsNegotiationService;
import me.zort.acs.api.domain.access.RightsHolder;
import me.zort.acs.api.domain.service.DefinitionsService;
import me.zort.acs.api.domain.service.GrantService;
import me.zort.acs.api.domain.service.GroupService;
import me.zort.acs.domain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class RightsNegotiationServiceImpl implements RightsNegotiationService {
    private final GrantService grantService;
    private final DefinitionsService definitionsService;
    private final GroupService groupService;

    @Override
    public List<RightsHolder> getRightsHolders(Subject accessor, Subject accessed) {
        SubjectType accessorType = accessor.getSubjectType();
        SubjectType accessedType = accessed.getSubjectType();

        // Nodes granted by definitions source
        Set<Node> defaultGrantedNodes = definitionsService
                .getDefaultGrantedNodes(accessorType, accessedType);

        // Nodes granted by external requests
        List<Grant> grants = grantService.getGrants(accessor, accessed);

        // Groups the subject is member of
        List<Group> groups = groupService.getGroupMemberships(accessor, accessedType);

        List<RightsHolder> holders = new ArrayList<>();
        holders.add(NodesBulk.of(defaultGrantedNodes));
        holders.addAll(grants);
        holders.addAll(groups);

        return holders;
    }
}
