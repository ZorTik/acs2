package me.zort.acs.plane.api.domain.storage;

/**
 * Configuration interface for blob storage paths.
 */
public interface BlobPathConfig {

    /**
     * Returns the prefix path for storing rule sets in the blob storage.
     *
     * @return the prefix path for rule sets
     */
    BlobPath getRuleSetsPathPrefix();
}
