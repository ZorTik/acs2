package me.zort.acs.plane.data.storage;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.storage.BlobObject;
import me.zort.acs.plane.api.domain.storage.BlobPath;
import me.zort.acs.plane.api.data.storage.BlobObjectRepository;
import me.zort.acs.plane.api.domain.storage.FileSystemBlobObject;
import me.zort.acs.plane.api.domain.storage.exception.BlobObjectCollisionException;
import me.zort.acs.plane.api.domain.storage.exception.BlobObjectNotExistsException;
import me.zort.acs.plane.api.domain.storage.exception.BlobObjectTransferException;

import java.io.*;
import java.nio.file.Files;

@RequiredArgsConstructor
public class FileSystemBlobObjectRepository implements BlobObjectRepository {
    private final File storageDir;

    @Override
    public void put(BlobPath path, BlobObject object) throws BlobObjectCollisionException {
        File file = path.toRelativeFile(storageDir, ".jar");
        if (Files.exists(file.toPath())) {
            throw new BlobObjectCollisionException();
        }

        try {
            ensureFile(file);

            try (OutputStream out = new FileOutputStream(file)) {
                object.transferTo(out);
            }
        } catch (IOException e) {
            throw new BlobObjectTransferException(e);
        }
    }

    @Override
    public BlobObject get(BlobPath path) throws BlobObjectNotExistsException {
        File file = path.toRelativeFile(storageDir, ".jar");
        if (!Files.exists(file.toPath())) {
            throw new BlobObjectNotExistsException();
        }

        return new FileSystemBlobObject(file);
    }

    @Override
    public void delete(BlobPath path) {
        File file = path.toRelativeFile(storageDir, ".jar");

        if (!Files.exists(file.toPath())) {
            return;
        }

        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void ensureFile(File file) throws IOException {
        Files.createDirectories(file.toPath().getParent());

        Files.createFile(file.toPath());
    }
}
