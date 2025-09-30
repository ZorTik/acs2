package me.zort.acs.plane.domain.storage;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.data.storage.BlobObjectRepository;
import me.zort.acs.plane.api.domain.storage.*;
import me.zort.acs.plane.api.domain.storage.exception.BlobObjectCollisionException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BlobStorageServiceImpl implements BlobStorageService {
    private final BlobObjectRepository repository;

    @Override
    public void store(BlobPath path, BlobObject object) throws IllegalArgumentException, BlobObjectCollisionException {
        Objects.requireNonNull(path, "Path cannot be null");
        Objects.requireNonNull(object, "BlobObject cannot be null");

        repository.put(path, object);
    }

    @Override
    public BlobObject retrieve(BlobPath path) throws IllegalArgumentException {
        Objects.requireNonNull(path, "Path cannot be null");

        return repository.get(path);
    }

    @Override
    public void delete(BlobPath path) {
        repository.delete(path);
    }
}
