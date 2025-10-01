package me.zort.acs.plane.api.domain.storage;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;

public interface BlobObject {

    /**
     * Transfer the object's data in the out stream.
     *
     * @param out The out stream
     * @throws IOException The exception, if any
     */
    void transferTo(OutputStream out) throws IOException;

    static @NotNull BlobObject of(byte[] bytes) {
        return new InMemoryBlobObject(bytes);
    }
}
