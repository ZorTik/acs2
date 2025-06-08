package me.zort.acs.domain.util;

import lombok.experimental.UtilityClass;
import me.zort.acs.domain.model.Group;
import org.apache.commons.collections4.set.UnmodifiableSet;

import java.util.HashSet;
import java.util.Set;

@UtilityClass
public final class GroupUtils {

    public static boolean detectCircularDependency(Group group, Set<Group> visited) {
        if (visited instanceof UnmodifiableSet<Group>) {
            visited = new HashSet<>(visited); // Ensure we can modify the set
        }

        if (visited.contains(group)) {
            return true; // Circular dependency detected
        }

        visited.add(group);

        if (group.getParent() != null) {
            return detectCircularDependency(group.getParent(), visited);
        } else {
            return false; // No circular dependency
        }
    }
}
