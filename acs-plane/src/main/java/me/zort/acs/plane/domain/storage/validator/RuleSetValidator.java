package me.zort.acs.plane.domain.storage.validator;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.storage.BlobObject;
import me.zort.acs.plane.api.domain.storage.BlobObjectValidator;
import me.zort.acs.plane.api.domain.storage.BlobPathConfig;
import me.zort.acs.plane.api.domain.storage.exception.BlobObjectValidationException;

import java.util.regex.Pattern;

@RequiredArgsConstructor
public class RuleSetValidator implements BlobObjectValidator {
    private final BlobPathConfig blobPathConfig;

    @Override
    public void validate(BlobObject object) throws BlobObjectValidationException {
        // TODO: Validate rule set jar
    }

    @Override
    public Pattern getPathPattern() {
        return blobPathConfig.getRuleSetsPathPrefix().getMatchSubfilesPattern();
    }
}
