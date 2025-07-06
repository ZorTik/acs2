package me.zort.acs.plane.api.domain.realm;

import me.zort.acs.plane.api.domain.realm.exception.RealmAlreadyExistsException;
import me.zort.acs.plane.api.domain.realm.exception.RealmNotExistsException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing realms.
 * <p>
 * Provides methods for creating, deleting, and retrieving realms.
 * </p>
 */
public interface RealmService {

    /**
     * Creates a new realm with the given name.
     *
     * @param realm the name of the realm to create
     * @return the created {@link Realm} instance
     * @throws RealmAlreadyExistsException if a realm with the given name already exists
     */
    Realm createRealm(String realm) throws RealmAlreadyExistsException;

    /**
     * Deletes the specified realm.
     *
     * @param realm the {@link Realm} to delete
     * @throws RealmNotExistsException if the specified realm does not exist
     */
    void deleteRealm(Realm realm) throws RealmNotExistsException;

    /**
     * Retrieves a realm by its name.
     *
     * @param realm the name of the realm to retrieve
     * @return an {@link Optional} containing the found {@link Realm}, or empty if not found
     */
    Optional<Realm> getRealm(String realm);

    List<Realm> getAllRealms();
}
