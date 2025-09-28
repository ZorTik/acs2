package me.zort.acs.plane.api.domain.storage;

import me.zort.acs.plane.api.domain.storage.exception.BlobObjectCollisionException;
import me.zort.acs.plane.api.domain.storage.exception.BlobObjectNotExistsException;

public interface BlobStorageService {

    /**
     * Stores the given BlobObject at the specified BlobPath.
     *
     * @param path the path where the BlobObject should be stored
     * @param object the BlobObject to be stored
     * @throws IllegalArgumentException if the path is invalid
     * @throws BlobObjectCollisionException if a BlobObject already exists at the specified path
     */
    void store(BlobPath path, BlobObject object) throws IllegalArgumentException, BlobObjectCollisionException;

    /**
     * Retrieves the BlobObject stored at the specified BlobPath.
     *
     * @param path the path of the BlobObject to retrieve
     * @return the BlobObject stored at the specified path, or null if no object exists at that path
     * @throws IllegalArgumentException if the path is invalid
     * @throws BlobObjectNotExistsException if no BlobObject exists at the specified path
     */
    BlobObject retrieve(BlobPath path) throws IllegalArgumentException, BlobObjectNotExistsException;
}
