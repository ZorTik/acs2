package me.zort.acs.domain.model;

public interface Grant {

    boolean appliesTo(Node node);

    Subject getAccessor();

    Subject getAccessed();
}
