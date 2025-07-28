package me.zort.acs.api.domain.operation;

/**
 * Indicates that this operation should be automatically committed after execution.
 *
 * @param <O> the type of object this operation operates on
 */
public interface AutoCommittableOperation<O> extends Operation<O> {
}
