package me.zort.acs.plane.api.http.mapper;

public interface HttpToDomainMapper<H, D> {

    /**
     * Converts an HTTP dto object to a domain object.
     *
     * @param http the HTTP dto object to convert
     * @return the converted domain object
     */
    D toDomain(H http);
}
