package me.zort.acs.api.domain.operation;

public interface OperationExecutor<O> {

    <OP extends Operation<O>> boolean executeOperation(OP operation, O object);
}
