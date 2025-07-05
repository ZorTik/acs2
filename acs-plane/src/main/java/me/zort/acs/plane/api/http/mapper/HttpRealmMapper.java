package me.zort.acs.plane.api.http.mapper;

import me.zort.acs.plane.api.domain.realm.Realm;

/**
 * Maps HTTP request data to a {@link Realm} object.
 * The data here represent Realm's name.
 */
public interface HttpRealmMapper extends HttpToDomainMapper<String, Realm> {
}
