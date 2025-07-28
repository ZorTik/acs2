package me.zort.acs.api.domain.operation;

public interface CommittableCallContext<O> extends OperationCallContext {

    void commit(O object);
}
