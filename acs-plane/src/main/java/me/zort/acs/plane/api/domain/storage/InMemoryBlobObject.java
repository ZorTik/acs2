package me.zort.acs.plane.api.domain.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InMemoryBlobObject implements BlobObject {
    private final String mimeType;
    private final byte[] bytes;

}
