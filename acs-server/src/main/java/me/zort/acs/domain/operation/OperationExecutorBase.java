package me.zort.acs.domain.operation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zort.acs.api.data.repository.SaveRepository;
import me.zort.acs.api.domain.operation.*;
import me.zort.acs.api.domain.operation.exception.OperationCriticalException;
import me.zort.acs.core.domain.mapper.DomainToPersistenceMapper;
import org.jetbrains.annotations.NotNull;

@Slf4j
@RequiredArgsConstructor
public class OperationExecutorBase<O, E> implements OperationExecutor<O> {
    private final SaveRepository<E> repository;
    private final DomainToPersistenceMapper<O, E> mapper;

    @Override
    public <OP extends Operation<O>> boolean executeOperation(OP operation, O object) {
        try {
            CommittableCallContext<O> callContext = createCallContext();

            operation.execute(object, callContext);

            if (operation instanceof AutoCommittableOperation<?>) {
                callContext.commit(object);
            }
            return true;
        } catch (RuntimeException e) {
            if (e instanceof OperationCriticalException critical) {
                log.error(
                        "Failed to execute operation {} on object {}",
                        operation.getClass().getSimpleName(), object.toString(), e);

                // TODO: Handle extraordinary cases, prob log to database
            }

            return false;
        }
    }

    private @NotNull CommittableCallContext<O> createCallContext() {
        return new CommittableCallContext<O>() {
            private boolean committed;

            @Override
            public void commit(O object) {
                if (committed) {
                    throw new IllegalStateException("Object has already been committed.");
                }

                commitObject(object);
                committed = true;
            }
        };
    }

    private void commitObject(O object) {
        E entity = mapper.toPersistence(object);
        if (entity == null) {
            throw new IllegalStateException("Entity cannot be null after mapping.");
        }

        repository.save(entity);
    }
}
