package me.zort.acs.domain.access.rights;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.rights.RightsNegotiationService;
import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.api.domain.model.Grant;
import me.zort.acs.api.domain.service.DefinitionsService;
import me.zort.acs.api.domain.service.GrantService;
import me.zort.acs.api.domain.service.GroupService;
import me.zort.acs.domain.access.NodesBulk;
import me.zort.acs.domain.group.Group;
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
        List<RightsHolder> holdersOfGrants = grantService.getGrants(accessor, accessed)
                .stream()
                .map(Grant::getRightsHolder).toList();

        // Groups the subject is member of
        List<Group> groups = groupService.getGroupMemberships(accessor, accessed);

        List<RightsHolder> holders = new ArrayList<>();
        holders.add(NodesBulk.of(defaultGrantedNodes));
        holders.addAll(holdersOfGrants);
        holders.addAll(groups);

        return holders;
    }
}
