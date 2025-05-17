package me.zort.acs.domain.check;

import me.zort.acs.domain.model.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Composite implementation of RightsStrategy that delegates the node applicability
 * check to a collection of other RightsStrategy implementations.
 *
 * <p>
 * It aggregates multiple RightsStrategy beans and checks if any of them
 * considers the node applicable on the given target node.
 * </p>
 */
@Primary
@Component
public class CompositeRightsStrategy implements RightsStrategy {
    private final List<RightsStrategy> strategies;

    /**
     * Constructs a new CompositeRightsStrategy with the given strategies.
     * This filters those strategies that are themselves CompositeRightsStrategy to
     * avoid infinite recursion.
     *
     * @param strategies all available RightsStrategy beans
     */
    @Autowired
    public CompositeRightsStrategy(List<RightsStrategy> strategies) {
        this.strategies = strategies
                .stream()
                .filter(strategy -> !(strategy instanceof CompositeRightsStrategy))
                .toList();
    }

    /**
     * Checks if any of the delegated strategies considers the 'node' applicable on 'on'.
     *
     * @param node the node to check applicability for
     * @param on   the node to check against
     * @return true if any strategy matches; false otherwise
     */
    @Override
    public boolean isNodeApplicableOn(Node node, Node on) {
        return strategies.stream().anyMatch(strategy -> strategy.isNodeApplicableOn(node, on));
    }
}