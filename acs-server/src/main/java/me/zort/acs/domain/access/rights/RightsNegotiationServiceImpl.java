package me.zort.acs.domain.access.rights;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.rights.RightsNegotiationService;
import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.api.domain.model.Grant;
import me.zort.acs.api.domain.model.SubjectLike;
import me.zort.acs.api.domain.service.DefinitionsService;
import me.zort.acs.api.domain.grant.GrantService;
import me.zort.acs.api.domain.service.GroupService;
import me.zort.acs.domain.access.NodesBulk;
import me.zort.acs.domain.group.Group;
import me.zort.acs.domain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class RightsNegotiationServiceImpl implements RightsNegotiationService {
    private final GrantService grantService;
    private final DefinitionsService definitionsService;
    private final GroupService groupService;

    @Override
    public List<RightsHolder> getRightsHolders(SubjectLike accessorLike, SubjectLike accessedLike) {
        SubjectType accessorType = accessorLike.getSubjectType();
        SubjectType accessedType = accessedLike.getSubjectType();

        List<RightsHolder> holders = new ArrayList<>();

        // Default nodes and groups granted by definition source
        holders.add(NodesBulk.of(definitionsService
                .getDefaultGrantedNodes(accessorType, accessedType)));
        holders.addAll(definitionsService
                .getDefaultGrantedGroups(accessorType, accessedType));

        // Holders applicable only between existing subjects
        if (accessorLike instanceof Subject accessor && accessedLike instanceof Subject accessed) {
            // Nodes granted by external requests
            List<RightsHolder> holdersOfGrants = grantService.getGrants(accessor, accessed)
                    .stream()
                    .map(Grant::getRightsHolder).toList();

            // Groups the subject is member of
            List<Group> groups = groupService.getGroupMemberships(accessor, accessed);

            holders.addAll(holdersOfGrants);
            holders.addAll(groups);
        }

        return holders;
    }
}
