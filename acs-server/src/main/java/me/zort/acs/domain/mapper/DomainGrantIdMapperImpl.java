package me.zort.acs.domain.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.mapper.DomainGrantIdMapper;
import me.zort.acs.api.domain.model.SubjectLike;
import me.zort.acs.data.id.GrantId;
import me.zort.acs.domain.model.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class DomainGrantIdMapperImpl extends DomainGrantIdMapper {

    @Override
    public GrantId toPersistence(SubjectLike accessor, SubjectLike accessed, Node node) {
        return new GrantId(
                accessor.getId(),
                accessor.getSubjectTypeId(),
                accessed.getId(), accessed.getSubjectTypeId(), node.getValue());
    }
}
