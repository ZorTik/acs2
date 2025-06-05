package me.zort.acs.api.domain.access;

import me.zort.acs.domain.model.SubjectType;

public interface RightsHolderPresenceVerifier {

    boolean isPresentInSubjectType(SubjectType subjectType, RightsHolder rightsHolder);
}
