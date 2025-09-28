package me.zort.acs.plane.api.domain.storage;

import me.zort.acs.plane.api.domain.storage.exception.BlobObjectValidationException;

import java.util.regex.Pattern;

public interface BlobObjectValidator {

    /**
     * Validates the given BlobObject.
     *
     * @param object the BlobObject to validate
     * @throws BlobObjectValidationException if the object is invalid
     */
    void validate(BlobObject object) throws BlobObjectValidationException;

    /**
     * Returns the path pattern that this validator applies to.
     *
     * @return the path pattern as a Pattern object
     */
    Pattern getPathPattern();
}
