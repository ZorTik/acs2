package me.zort.acs.plane.http.mapper;

import me.zort.acs.core.domain.definitions.format.DefinitionsFormat;
import me.zort.acs.plane.api.http.exception.HttpUnknownDefinitionsFormatException;
import me.zort.acs.plane.api.http.mapper.HttpFormatMapper;
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
    public DefinitionsFormat fromMimeType(String mimeType) throws HttpUnknownDefinitionsFormatException {
        DefinitionsFormat format = DefinitionsFormat.fromMimeType(mimeType);

        if (format == null && !mimeType.isEmpty()) {
            throw new HttpUnknownDefinitionsFormatException(mimeType);
        } else {
            format = defaultFormat;
        }
        return format;
    }
}
