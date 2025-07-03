package me.zort.acs.plane.api.domain.realm;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for persisting and retrieving {@link Realm} entities.
 * Provides methods for saving, deleting, fetching, and checking the existence of realms.
 */
public interface RealmPersistenceService {

    /**
     * Saves the given realm entity.
     *
     * @param realm the realm to save
     */
    void saveRealm(Realm realm);

    /**
     * Deletes the realm with the specified ID.
     *
     * @param id the ID of the realm to delete
     */
    void deleteRealm(String id);

    /**
     * Retrieves the realm with the specified ID.
     *
     * @param id the ID of the realm to retrieve
     * @return an {@link Optional} containing the realm if found, or empty if not found
     */
    Optional<Realm> getRealm(String id);

    /**
     * Checks if a realm with the specified ID exists.
     *
     * @param id the ID of the realm to check
     * @return {@code true} if the realm exists, {@code false} otherwise
     */
    boolean existsRealm(String id);

    List<Realm> getAllRealms();
}
