package me.zort.acs.api.domain.access.request;

public interface AccessRequest {

    void grant();

    boolean isGranted();
}
