package me.zort.acs.plane.api.http.mapper;

import java.util.Optional;

public interface HttpToDomainMapper<H, D> {

    /**
     * Converts an HTTP dto object to a domain object.
     *
     * @param http the HTTP dto object to convert
     * @return the converted domain object or an empty Optional if the conversion failed
     */
    Optional<D> toDomain(H http);
}
