package me.zort.acs.plane.domain.storage;

import me.zort.acs.plane.api.domain.storage.BlobObject;
import me.zort.acs.plane.api.domain.storage.BlobObjectValidator;
import me.zort.acs.plane.api.domain.storage.BlobPath;
import me.zort.acs.plane.api.domain.storage.BlobStorageValidationService;
import me.zort.acs.plane.api.domain.storage.exception.BlobObjectValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlobStorageValidationServiceImpl implements BlobStorageValidationService {
    private final List<BlobObjectValidator> validators;

    public BlobStorageValidationServiceImpl(List<BlobObjectValidator> validators) {
        this.validators = validators;
    }

    @Override
    public void addValidator(BlobObjectValidator validator) {
        validators.add(validator);
    }

    @Override
    public void validateAtPath(BlobPath path, BlobObject object) throws BlobObjectValidationException {
        for (BlobObjectValidator validator : validators) {
            if (!validator.getPathPattern().matcher(path.toString()).matches()) {
                continue;
            }

            validator.validate(object);
        }
    }
}
