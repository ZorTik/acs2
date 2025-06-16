package me.zort.acs.api.domain.access.rights;

import me.zort.acs.domain.model.Node;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public interface RightsHolder {

    Set<Node> getGrantedNodes();

    @Nullable
    default String getIdentifier() {
        return null;
    }
}
