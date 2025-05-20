package me.zort.acs.domain.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.data.id.GrantId;
import me.zort.acs.domain.model.Grant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class DomainGrantIdMapper implements DomainModelMapper<Grant, GrantId> {

    @Override
    public GrantId toPersistence(Grant domain) {
        return new GrantId(
                domain.getAccessor().getId(),
                domain.getAccessor().getSubjectTypeId(),
                domain.getAccessed().getId(), domain.getAccessed().getSubjectTypeId(), domain.getNode().getValue());
    }
}
