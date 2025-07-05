package me.zort.acs.core.data.entity;

public interface AcsEntity<ID> {

    void setId(ID id);

    ID getId();
}
