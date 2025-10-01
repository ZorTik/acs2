package me.zort.acs.plane.api.domain.storage;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.OutputStream;

@AllArgsConstructor
public class InMemoryBlobObject implements BlobObject {
    private final byte[] bytes;

    @Override
    public void transferTo(OutputStream out) throws IOException {
        out.write(bytes);
    }
}
