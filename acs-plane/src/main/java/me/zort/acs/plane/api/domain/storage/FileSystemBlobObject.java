package me.zort.acs.plane.api.domain.storage;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;

@RequiredArgsConstructor
public class FileSystemBlobObject implements BlobObject {
    private final File file;

    @Override
    public void transferTo(OutputStream out) throws IOException {
        Files.copy(file.toPath(), out);
    }

    @Override
    public @NotNull InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }
}
