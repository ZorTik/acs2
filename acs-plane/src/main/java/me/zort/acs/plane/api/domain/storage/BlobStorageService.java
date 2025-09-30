package me.zort.acs.plane.api.domain.storage;

import me.zort.acs.plane.api.domain.storage.exception.BlobObjectCollisionException;

public interface BlobStorageService {

    /**
     * Stores the given BlobObject at the specified BlobPath.
     *
     * @param path the path where the BlobObject should be stored
     * @param object the BlobObject to be stored
     * @throws BlobObjectCollisionException if a BlobObject already exists at the specified path
     */
    void store(BlobPath path, BlobObject object) throws BlobObjectCollisionException;

    /**
     * Retrieves the BlobObject stored at the specified BlobPath.
     *
     * @param path the path of the BlobObject to retrieve
     * @return the BlobObject stored at the specified path
     * @throws IllegalArgumentException if the path is invalid or does not exist
     */
    BlobObject retrieve(BlobPath path) throws IllegalArgumentException;

    void delete(BlobPath path);
}
