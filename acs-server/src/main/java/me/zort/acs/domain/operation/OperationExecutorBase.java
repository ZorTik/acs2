package me.zort.acs.domain.operation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zort.acs.core.domain.mapper.DomainToPersistenceMapper;
import me.zort.acs.api.domain.operation.Operation;
import me.zort.acs.api.domain.operation.OperationExecutor;
import org.springframework.data.repository.CrudRepository;

@Slf4j
@RequiredArgsConstructor
public class OperationExecutorBase<O, E> implements OperationExecutor<O> {
    private final CrudRepository<E, ?> repository;
    private final DomainToPersistenceMapper<O, E> mapper;

    @Override
    public <OP extends Operation<O>> boolean executeOperation(OP operation, O object) {
        try {
            operation.execute(object);

            if (operation.isAutoCommit()) {
                commitObject(object);
            }
            return true;
        } catch (RuntimeException e) {
            log.error(
                    "Failed to execute operation {} on object {}",
                    operation.getClass().getSimpleName(), object.toString(), e);

            return false;
        }
    }

    private void commitObject(O object) {
        E entity = mapper.toPersistence(object);
        if (entity == null) {
            throw new IllegalStateException("Entity cannot be null after mapping.");
        }

        repository.save(entity);
    }
}
