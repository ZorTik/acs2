package me.zort.acs.domain.operation;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.mapper.DomainToPersistenceMapper;
import me.zort.acs.api.domain.operation.AutoCommitOperation;
import me.zort.acs.api.domain.operation.Operation;
import org.springframework.data.repository.CrudRepository;

@RequiredArgsConstructor
public class AutoCommitOperationExecutor<O, E> extends BaseOperationExecutor<O> {
    private final CrudRepository<E, ?> repository;
    private final DomainToPersistenceMapper<O, E> mapper;

    @Override
    public <OP extends Operation<O>> boolean executeOperation(OP operation, O object) {
        boolean result = super.executeOperation(operation, object);
        if (result && operation instanceof AutoCommitOperation<?> aco && aco.isAutoCommit()) {
            commitObject(object);
        }

        return result;
    }

    private void commitObject(O object) {
        E entity = mapper.toPersistence(object);
        if (entity == null) {
            throw new IllegalStateException("Entity cannot be null after mapping.");
        }

        repository.save(entity);
    }
}
