package me.zort.acs.core.domain.access.request;

/**
 * Interface representing an access request in the system.
 *
 * @author ZorTik
 */
public interface AccessRequest {

    /**
     * Grants the access request.
     */
    void grant();

    /**
     * Checks if the access request has been granted.
     *
     * @return true if the access request is granted, false otherwise
     */
    boolean isGranted();
}
