package me.zort.acs.plane.api.domain.storage;

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
}
