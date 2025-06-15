package me.zort.acs.config;

import me.zort.acs.api.data.repository.GroupRepository;
import me.zort.acs.core.domain.mapper.DomainToPersistenceMapper;
import me.zort.acs.api.domain.operation.OperationExecutor;
import me.zort.acs.data.entity.GroupEntity;
import me.zort.acs.domain.group.Group;
import me.zort.acs.domain.operation.OperationExecutorBase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OperationExecutorConfig {

    @Bean
    public OperationExecutor<Group> groupExecutor(
            GroupRepository repository, DomainToPersistenceMapper<Group, GroupEntity> mapper) {
        return new OperationExecutorBase<>(repository, mapper);
    }
}
