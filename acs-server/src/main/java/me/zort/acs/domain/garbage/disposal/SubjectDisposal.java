package me.zort.acs.domain.garbage.disposal;

import lombok.RequiredArgsConstructor;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.service.GrantService;
import me.zort.acs.domain.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class SubjectDisposal extends ResourceDisposal<Subject> {
    private final GrantService grantService;
    private final SubjectService subjectService;

    @Override
    public void dispose(Subject resource) {
        subjectService.deleteSubject(resource);
    }

    @Override
    public boolean shouldDispose(Subject resource) {
        return grantService.getGrantsCount(resource) == 0;
    }

    @Override
    public Class<Subject> getResourceType() {
        return Subject.class;
    }
}
