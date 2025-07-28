package me.zort.acs.api.domain.operation;

/**
 * An operation that commits itself using the provided context.
 *
 * @param <O> the type of object this operation operates on
 * @author ZorTik
 */
public abstract class CommittableOperation<O> implements Operation<O> {

    /**
     * Executes the operation on the given object using the provided context.
     * This should hereby commit the operation.
     *
     * @param object the object to operate on
     * @param context the context in which the operation is executed, which must be a {@link CommittableCallContext}
     * @throws RuntimeException if an error occurs during execution
     */
    public abstract void doExecute(O object, CommittableCallContext<O> context) throws RuntimeException;

    // Type-safe
    @SuppressWarnings("unchecked")
    @Override
    public final void execute(O object, OperationCallContext context) throws RuntimeException {
        if (!(context instanceof CommittableCallContext)) {
            throw new IllegalArgumentException("Context must be an instance of CommittableCallContext");
        }

        doExecute(object, (CommittableCallContext<O>) context);
    }
}
