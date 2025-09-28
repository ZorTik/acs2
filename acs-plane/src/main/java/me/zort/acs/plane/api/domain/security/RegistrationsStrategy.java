package me.zort.acs.plane.api.domain.security;

public interface RegistrationsStrategy {

    /**
     * Returns if the system allows user registrations at the current moment.
     *
     * @return true if registrations are allowed, false otherwise
     */
    boolean isRegistrationAllowed();
}
