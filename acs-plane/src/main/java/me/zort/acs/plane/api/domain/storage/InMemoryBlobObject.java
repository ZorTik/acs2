package me.zort.acs.plane.api.domain.storage;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@AllArgsConstructor
public class InMemoryBlobObject implements BlobObject {
    private final byte[] bytes;

    @Override
    public void transferTo(OutputStream out) throws IOException {
        out.write(bytes);
    }

    @Override
    public @NotNull InputStream getInputStream() {
        return new ByteArrayInputStream(bytes);
    }
}
