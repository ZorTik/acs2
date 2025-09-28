package me.zort.acs.plane.data.storage;

import me.zort.acs.plane.api.domain.storage.BlobObject;
import me.zort.acs.plane.api.domain.storage.BlobPath;
import me.zort.acs.plane.api.data.storage.BlobObjectRepository;
import me.zort.acs.plane.api.domain.storage.exception.BlobObjectCollisionException;
import me.zort.acs.plane.api.domain.storage.exception.BlobObjectNotExistsException;
import org.springframework.stereotype.Service;

@Service
public class FileSystemBlobObjectRepository implements BlobObjectRepository {

    @Override
    public void put(BlobPath path, BlobObject object) throws BlobObjectCollisionException {
        // TODO
    }

    @Override
    public BlobObject get(BlobPath path) throws BlobObjectNotExistsException, IllegalArgumentException {
        // TODO
    }

    @Override
    public void delete(BlobPath path) {
        // TODO
    }
}
