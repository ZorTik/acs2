package me.zort.acs.domain.access.strategy;

import me.zort.acs.domain.model.Node;

/**
 * Strategy interface for defining different approaches
 * to evaluate rights or permissions within the system.
 * <p></p>
 * Implementations encapsulate specific logic related to
 * access control, node applicability, or other related checks.
 */
public interface RightsStrategy {

    /**
     * Checks if the given node is applicable on the given node.
     * <p></p>
     * Meaning, if this returns true, the "node" marks the calling subject
     * as having access to the "on" node.
     *
     * @param node The node to check.
     * @param on   The node to check against.
     * @return True if the node is applicable on the given node, false otherwise.
     */
    boolean isNodeApplicableOn(Node node, Node on);
}
