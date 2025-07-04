package me.zort.acs.plane.http.mapper;

import me.zort.acs.core.domain.definitions.format.DefinitionsFormat;
import me.zort.acs.plane.api.http.mapper.HttpFormatMapper;
import me.zort.acs.plane.facade.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class HttpFormatMapperImpl implements HttpFormatMapper {
    private final DefinitionsFormat defaultFormat;

    @Autowired
    public HttpFormatMapperImpl(@Qualifier("default") DefinitionsFormat defaultFormat) {
        this.defaultFormat = defaultFormat;
    }

    @Override
    public Result<DefinitionsFormat> fromMimeType(String mimeType) {
        DefinitionsFormat format = DefinitionsFormat.fromMimeType(mimeType);
        if (format == null && !mimeType.isEmpty()) {
            return Result.error(400, String.format("Unknown definitions format for mime type %s", mimeType));
        } else {
            format = defaultFormat;
        }

        return Result.ok(format);
    }
}
