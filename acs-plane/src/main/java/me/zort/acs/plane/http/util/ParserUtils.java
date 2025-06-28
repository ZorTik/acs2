package me.zort.acs.plane.http.util;

import lombok.experimental.UtilityClass;
import me.zort.acs.core.domain.definitions.format.DefinitionsFormat;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.util.MimeType;

import java.io.IOException;

@UtilityClass
public final class ParserUtils {

    public static @NotNull DefinitionsModel parseModel(Resource resource, MimeType mimeType) throws IOException {
        if (!resource.exists()) {
            throw new IllegalArgumentException("Resource " + resource + " does not exist");
        }

        DefinitionsFormat format = DefinitionsFormat.fromMimeType(mimeType.toString());
        if (format == null) {
            throw new IllegalArgumentException(
                    "Unsupported mime type "  + mimeType + " for definitions parsing");
        }

        return format.parseModel(resource.getInputStream());
    }
}
