package me.zort.acs.domain.access.rule;

import me.zort.acs.core.domain.access.request.SubjectToSubjectAccessRequest;
import me.zort.acs.core.domain.access.rights.RightsHolder;
import me.zort.acs.core.domain.access.rule.SubjectToSubjectAccessRule;
import me.zort.acs.core.model.SubjectLike;
import me.zort.acs.core.model.SubjectType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SelfAccessRule extends SubjectToSubjectAccessRule {

    public SelfAccessRule() {
        super(true);
    }

    @Override
    public void onRequest(SubjectToSubjectAccessRequest request) {
        if (request.getAccessor().equals(request.getAccessed())) {
            request.grant();
        }
    }

    @Override
    public Page<? extends SubjectLike> queryForAccessibleSubjects(
            SubjectLike accessor, SubjectType targetSubjectType, List<RightsHolder> rightsHolders, Pageable pageable) {
        if (!accessor.getSubjectType().equals(targetSubjectType)) {
            return Page.empty(pageable);
        } else {
            return new PageImpl<>(List.of(accessor), pageable, 1);
        }
    }
}
