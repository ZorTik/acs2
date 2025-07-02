package me.zort.acs.plane.api.http.mapper;

import me.zort.acs.plane.api.http.error.HttpError;

public interface HttpToDomainMapper<H, D> {

    /**
     * Converts an HTTP dto object to a domain object.
     *
     * @param http the HTTP dto object to convert
     * @return the converted domain object
     * @throws HttpError if the conversion fails
     */
    D toDomain(H http) throws HttpError;
}
