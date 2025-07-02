package me.zort.acs.plane.facade;

import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.definitions.exception.DefinitionsParseException;
import me.zort.acs.core.domain.definitions.exception.InvalidDefinitionsException;
import me.zort.acs.core.domain.definitions.format.DefinitionsFormat;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs.plane.api.domain.definitions.DefinitionsService;
import me.zort.acs.plane.api.domain.realm.Realm;
import me.zort.acs.plane.api.facade.DefinitionsFacade;
import me.zort.acs.plane.api.http.error.HttpError;
import me.zort.acs.plane.api.http.mapper.HttpFormatMapper;
import me.zort.acs.plane.api.http.mapper.HttpToDomainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class DefinitionsFacadeImpl implements DefinitionsFacade {
    private final DefinitionsService definitionsService;
    private final HttpFormatMapper formatMapper;
    private final HttpToDomainMapper<String, Realm> realmMapper;

    @Override
    public void setDefinitions(Realm realm, String definitions, DefinitionsFormat format) throws HttpError {
        DefinitionsModel model;
        try (InputStream in = new ByteArrayInputStream(definitions.getBytes(StandardCharsets.UTF_8))) {
            model = format.parseModel(in);
        } catch (DefinitionsParseException e) {
            throw HttpError.of(400, "Invalid definitions format: " + e.getMessage());
        } catch (IOException e) {
            throw HttpError.of(500, "Failed to read definitions: " + e.getMessage());
        }

        try {
            definitionsService.setDefinitions(realm, model);
        } catch (InvalidDefinitionsException e) {
            throw HttpError.of(400, "Invalid definitions: " + e.getMessage());
        }
    }

    // TODO: Handle exceptions here and map them to HttpError
    @Override
    public String getDefinitions(String realmName, String formatMimeType) throws HttpError {
        DefinitionsFormat format = formatMapper.fromMimeType(formatMimeType);
        Realm realmObj = realmMapper.toDomain(realmName);

        DefinitionsModel model = realmObj.getDefinitionsModel();

        return format.toStringModel(model);
    }
}
