package me.zort.acs.api.domain.operation;

public interface AutoCommitOperation<O> extends Operation<O> {

    boolean isAutoCommit();
}
