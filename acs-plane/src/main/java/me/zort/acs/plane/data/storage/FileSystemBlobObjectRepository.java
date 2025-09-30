package me.zort.acs.plane.data.storage;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.storage.BlobObject;
import me.zort.acs.plane.api.domain.storage.BlobPath;
import me.zort.acs.plane.api.data.storage.BlobObjectRepository;
import me.zort.acs.plane.api.domain.storage.exception.BlobObjectCollisionException;

import java.io.File;

@RequiredArgsConstructor
public class FileSystemBlobObjectRepository implements BlobObjectRepository {
    private final File storageDir;

    @Override
    public void put(BlobPath path, BlobObject object) throws BlobObjectCollisionException {
        // TODO
    }

    @Override
    public BlobObject get(BlobPath path) throws IllegalArgumentException {
        // TODO
    }

    @Override
    public void delete(BlobPath path) {
        // TODO
    }
}
