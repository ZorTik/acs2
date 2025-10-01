package me.zort.acs.plane.api.domain.storage;

import me.zort.acs.plane.api.domain.storage.exception.BlobObjectCollisionException;
import me.zort.acs.plane.api.domain.storage.exception.BlobObjectNotExistsException;

public interface BlobStorageService {

    /**
     * Stores the given BlobObject at the specified BlobPath.
     *
     * @param path the path where the BlobObject should be stored
     * @param object the BlobObject to be stored
     * @throws BlobObjectCollisionException if a BlobObject already exists at the specified path
     */
    void store(BlobPath path, BlobObject object) throws BlobObjectCollisionException;

    default void storeForcibly(BlobPath path, BlobObject object) {
        delete(path);

        store(path, object);
    }

    /**
     * Retrieves the BlobObject stored at the specified BlobPath.
     *
     * @param path the path of the BlobObject to retrieve
     * @return the BlobObject stored at the specified path
     * @throws BlobObjectNotExistsException if the object does not exist at the given path
     */
    BlobObject retrieve(BlobPath path) throws BlobObjectNotExistsException;

    /**
     * Deletes a blob object at the given path.
     *
     * @param path The path to the blob object
     */
    void delete(BlobPath path);
}
