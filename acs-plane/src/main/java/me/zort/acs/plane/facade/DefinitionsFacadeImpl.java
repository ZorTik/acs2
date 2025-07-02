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
import me.zort.acs.plane.api.http.exception.HttpUnknownDefinitionsFormatException;
import me.zort.acs.plane.api.http.mapper.HttpFormatMapper;
import me.zort.acs.plane.api.http.mapper.HttpToDomainMapper;
import me.zort.acs.plane.facade.util.Result;
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
    public Result<Void> setDefinitions(Realm realm, String definitions, DefinitionsFormat format) {
        DefinitionsModel model;
        try (InputStream in = new ByteArrayInputStream(definitions.getBytes(StandardCharsets.UTF_8))) {
            model = format.parseModel(in);
        } catch (DefinitionsParseException e) {
            return Result.error(400, "Invalid definitions format: " + e.getMessage());
        } catch (IOException e) {
            return Result.error(500, "Failed to read definitions: " + e.getMessage());
        }

        try {
            definitionsService.setDefinitions(realm, model);
        } catch (InvalidDefinitionsException e) {
            return Result.error(400, "Invalid definitions: " + e.getMessage());
        }

        return Result.ok();
    }

    @Override
    public Result<String> getDefinitions(String realmName, String formatMimeType) {
        DefinitionsFormat format;
        try {
            format = formatMapper.fromMimeType(formatMimeType);
        } catch (HttpUnknownDefinitionsFormatException e) {
            return Result.error(400, e.getMessage());
        }

        Realm realmObj;
        try {
            realmObj = realmMapper.toDomain(realmName);
        } catch (HttpError e) {
            return Result.error(e);
        }

        DefinitionsModel model = realmObj.getDefinitionsModel();
        String value = format.toStringModel(model);

        return Result.ok(value);
    }
}
