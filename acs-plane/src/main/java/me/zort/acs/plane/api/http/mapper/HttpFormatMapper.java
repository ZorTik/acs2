package me.zort.acs.plane.api.http.mapper;

import me.zort.acs.core.domain.definitions.format.DefinitionsFormat;
import me.zort.acs.plane.facade.util.Result;

public interface HttpFormatMapper {

    Result<DefinitionsFormat> fromMimeType(String mimeType);
}
