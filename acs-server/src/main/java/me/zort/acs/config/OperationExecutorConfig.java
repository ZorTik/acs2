package me.zort.acs.config;

import me.zort.acs.api.data.repository.DynamicGroupRepository;
import me.zort.acs.api.data.repository.GroupRepository;
import me.zort.acs.api.data.repository.SubjectTypeRepository;
import me.zort.acs.core.domain.mapper.DomainToPersistenceMapper;
import me.zort.acs.api.domain.operation.OperationExecutor;
import me.zort.acs.data.entity.DynamicGroupEntity;
import me.zort.acs.data.entity.GroupEntity;
import me.zort.acs.api.domain.group.Group;
import me.zort.acs.data.entity.SubjectTypeEntity;
import me.zort.acs.core.model.SubjectType;
import me.zort.acs.domain.operation.OperationExecutorBase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OperationExecutorConfig {

    @Qualifier("staticGroupExecutor")
    @Bean
    public OperationExecutor<Group> groupExecutor(
            GroupRepository repository, DomainToPersistenceMapper<Group, GroupEntity> mapper) {
        return new OperationExecutorBase<>(repository, mapper);
    }

    @Qualifier("dynamicGroupExecutor")
    @Bean
    public OperationExecutor<Group> dynamicGroupExecutor(
            DynamicGroupRepository repository, DomainToPersistenceMapper<Group, DynamicGroupEntity> mapper) {
        return new OperationExecutorBase<>(repository, mapper);
    }

    @Bean
    public OperationExecutor<SubjectType> subjectTypeExecutor(
            SubjectTypeRepository repository, DomainToPersistenceMapper<SubjectType, SubjectTypeEntity> mapper) {
        return new OperationExecutorBase<>(repository, mapper);
    }
}
