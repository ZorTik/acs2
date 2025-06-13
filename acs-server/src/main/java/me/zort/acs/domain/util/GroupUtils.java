package me.zort.acs.domain.util;

import lombok.experimental.UtilityClass;
import me.zort.acs.domain.group.Group;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@UtilityClass
public final class GroupUtils {

    public static boolean detectCircularDependency(Group group, Group... initial) {
        Set<Group> initialSet = new HashSet<>(Arrays.asList(initial));

        return detectCircularDependency(group, initialSet);
    }

    private static boolean detectCircularDependency(Group group, Set<Group> visited) {
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
