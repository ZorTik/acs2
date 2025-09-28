package me.zort.acs.plane.domain.storage;

import me.zort.acs.plane.api.domain.storage.BlobPath;
import me.zort.acs.plane.api.domain.storage.BlobPathConfig;
import org.springframework.stereotype.Component;

@Component
public class DefaultBlobPathConfig implements BlobPathConfig {

    @Override
    public BlobPath getRuleSetsPathPrefix() {
        return BlobPath.compile("/rulesets");
    }
}
