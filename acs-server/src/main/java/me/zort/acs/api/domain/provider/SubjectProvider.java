package me.zort.acs.api.domain.provider;

import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;

public interface SubjectProvider {

    Subject getSubject(SubjectType type, String id);
}
