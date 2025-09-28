package me.zort.acs.plane.api.domain.storage;

import me.zort.acs.plane.api.domain.storage.exception.BlobObjectValidationException;

public interface BlobStorageValidationService {

    /**
     * Adds a new validator to the validation service.
     *
     * @param validator the validator to add
     */
    void addValidator(BlobObjectValidator validator);

    /**
     * Validates the given BlobObject using the registered validators.
     *
     * @param path the path where the BlobObject is to be stored
     * @param object the BlobObject to validate
     * @throws BlobObjectValidationException if the object is invalid
     */
    void validateAtPath(BlobPath path, BlobObject object) throws BlobObjectValidationException;
}
