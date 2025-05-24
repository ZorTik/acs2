package me.zort.acs.api.domain.service;

import me.zort.acs.api.domain.model.SubjectLike;
import me.zort.acs.domain.model.Node;

public interface DefinitionsService {

    void refresh();

    boolean checkDefaultGrant(SubjectLike from, SubjectLike to, Node node);
}
