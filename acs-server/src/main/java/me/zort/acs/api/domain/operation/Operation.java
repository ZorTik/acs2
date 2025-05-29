package me.zort.acs.api.domain.operation;

public interface Operation<O> {

    void execute(O object) throws RuntimeException;
}
