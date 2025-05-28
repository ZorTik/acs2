package me.zort.acs.domain.access.strategy;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.strategy.RightsStrategy;
import me.zort.acs.config.properties.AcsConfigurationProperties;
import me.zort.acs.domain.model.Group;
import me.zort.acs.domain.model.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This strategy represents hierarchical rights, meaning that if a node is a parent of another node,
 * it is applicable on that node.
 */
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class HierarchicalRightsStrategy implements RightsStrategy {
    private final AcsConfigurationProperties config;

    @Override
    public boolean isNodeApplicableOn(Node node, Node on) {
        return node.isParentOf(on, config.getDelimiter());
    }
}
