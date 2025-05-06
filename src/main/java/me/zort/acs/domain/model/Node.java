package me.zort.acs.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class Node {
    @Getter
    private final String value;
    private final List<SubjectType> subjectTypes;

    public boolean isParentOf(Node node, String delimiter) {
        String[] parentParts = this.value.split(delimiter);
        String[] childParts = node.getValue().split(delimiter);

        if (parentParts.length > childParts.length) {
            return false;
        }

        for (int i = 0; i < parentParts.length; i++) {
            if (parentParts[i].equals("*")) {
                continue;
            }
            if (!parentParts[i].equals(childParts[i])) {
                return false;
            }
        }
        return true;
    }

    public boolean isLeafNode() {
        return !value.endsWith("*");
    }

    public List<SubjectType> getSubjectTypes() {
        return List.copyOf(subjectTypes);
    }
}
