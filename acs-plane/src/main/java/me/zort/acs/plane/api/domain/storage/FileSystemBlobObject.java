package me.zort.acs.plane.api.domain.storage;

import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

@RequiredArgsConstructor
public class FileSystemBlobObject implements BlobObject {
    private final File file;

    @Override
    public void transferTo(OutputStream out) throws IOException {
        Files.copy(file.toPath(), out);
    }
}
