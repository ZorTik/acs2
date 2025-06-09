package me.zort.acs.api.domain.access.rights;

import me.zort.acs.domain.model.SubjectType;

public interface RightsHolderPresenceVerifier {

    boolean isPresentInSubjectType(SubjectType subjectType, RightsHolder rightsHolder);
}
