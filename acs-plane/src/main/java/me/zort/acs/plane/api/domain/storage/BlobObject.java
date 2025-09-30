package me.zort.acs.plane.api.domain.storage;

import org.jetbrains.annotations.NotNull;

public interface BlobObject {

    /**
     * Returns the MIME type of the data stored in this blob object.
     *
     * @return the MIME type as a string
     */
    String getMimeType();

    /**
     * Returns the data stored in this blob object as bytes.
     *
     * @return the data as bytes
     */
    byte[] getBytes();

    static @NotNull BlobObject of(String mimeType, byte[] bytes) {
        return new InMemoryBlobObject(mimeType, bytes);
    }
}
