package me.zort.acs.plane.api.http.mapper;

import me.zort.acs.core.domain.definitions.format.DefinitionsFormat;
import me.zort.acs.plane.api.http.exception.HttpUnknownDefinitionsFormatException;

public interface HttpFormatMapper {

    DefinitionsFormat fromMimeType(String mimeType) throws HttpUnknownDefinitionsFormatException;
}
