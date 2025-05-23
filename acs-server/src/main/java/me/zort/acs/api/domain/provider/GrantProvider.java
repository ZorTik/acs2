package me.zort.acs.api.domain.provider;

import me.zort.acs.domain.model.Grant;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;

public interface GrantProvider {

    Grant getGrant(Subject accessor, Subject accessed, Node node);
}
