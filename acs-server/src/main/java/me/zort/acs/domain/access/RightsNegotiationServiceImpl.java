package me.zort.acs.domain.access;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.RightsNegotiationService;
import me.zort.acs.api.domain.access.RightsHolder;
import me.zort.acs.api.domain.service.DefinitionsService;
import me.zort.acs.api.domain.service.GrantService;
import me.zort.acs.domain.model.Grant;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class RightsNegotiationServiceImpl implements RightsNegotiationService {
    private final GrantService grantService;
    private final DefinitionsService definitionsService;

    @Override
    public List<RightsHolder> getRightsHolders(Subject accessor, Subject accessed) {
        // Nodes granted by definitions source
        Set<Node> defaultGrantedNodes = definitionsService
                .getDefaultGrantedNodes(accessor.getSubjectType(), accessed.getSubjectType());

        // Nodes granted by external requests
        Set<Node> grantedNodes = grantService.getGrants(accessor, accessed)
                .stream()
                .filter(Grant::isValid)
                .map(Grant::getNode).collect(Collectors.toSet());

        // TODO: Groups, ty budou implementovat RightsHolder

        return List.of(
                NodesBulk.of(defaultGrantedNodes),
                NodesBulk.of(grantedNodes));
    }
}
