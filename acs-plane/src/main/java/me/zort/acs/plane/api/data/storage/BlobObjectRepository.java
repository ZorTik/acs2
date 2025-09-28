package me.zort.acs.plane.api.data.storage;

import me.zort.acs.plane.api.domain.storage.BlobObject;
import me.zort.acs.plane.api.domain.storage.BlobPath;
import me.zort.acs.plane.api.domain.storage.exception.BlobObjectCollisionException;
import me.zort.acs.plane.api.domain.storage.exception.BlobObjectNotExistsException;

public interface BlobObjectRepository {

    /**
     * Stores the given BlobObject at the specified BlobPath.
     *
     * @param path the path where the BlobObject should be stored
     * @param object the BlobObject to be stored
     */
    void put(BlobPath path, BlobObject object) throws BlobObjectCollisionException;

    /**
     * Retrieves the BlobObject stored at the specified BlobPath.
     *
     * @param path the path of the BlobObject to retrieve
     * @return the BlobObject stored at the specified path, or null if no object exists at that path
     * @throws IllegalArgumentException if the path is invalid
     * @throws BlobObjectNotExistsException if no BlobObject exists at the specified path
     */
    BlobObject get(BlobPath path) throws BlobObjectNotExistsException, IllegalArgumentException;

    /**
     * Deletes the BlobObject stored at the specified BlobPath.
     *
     * @param path the path of the BlobObject to delete
     */
    void delete(BlobPath path);
}
