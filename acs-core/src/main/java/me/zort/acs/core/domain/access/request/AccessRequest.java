package me.zort.acs.core.domain.access.request;

public interface AccessRequest {

    void grant();

    boolean isGranted();
}
