package me.zort.acs.plane.facade;

import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.definitions.exception.DefinitionsParseException;
import me.zort.acs.core.domain.definitions.exception.InvalidDefinitionsException;
import me.zort.acs.core.domain.definitions.format.DefinitionsFormat;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs.plane.api.domain.definitions.DefinitionsService;
import me.zort.acs.plane.api.domain.realm.Realm;
import me.zort.acs.plane.api.facade.DefinitionsFacade;
import me.zort.acs.plane.api.http.mapper.HttpFormatMapper;
import me.zort.acs.plane.facade.util.Result;
import org.jetbrains.annotations.NotNull;
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

    @Override
    public Result<Void> setDefinitions(Realm realm, String definitions, String formatMimeType) {
        return formatMapper
                .fromMimeType(formatMimeType).or(DefinitionsFormat.YAML)
                .flatMap(format -> parseDefinitionsModel(definitions, format))
                .flatMap(model -> setDefinitionsModel(realm, model));
    }

    @Override
    public Result<String> getDefinitions(Realm realm, String formatMimeType) {
        return formatMapper
                .fromMimeType(formatMimeType).or(DefinitionsFormat.YAML)
                .map(format -> format.toStringModel(realm.getDefinitionsModel()));
    }

    private static @NotNull Result<DefinitionsModel> parseDefinitionsModel(
            String definitions, DefinitionsFormat format) {
        try (InputStream in = new ByteArrayInputStream(definitions.getBytes(StandardCharsets.UTF_8))) {
            return Result.ok(format.parseModel(in));
        } catch (DefinitionsParseException e) {
            return Result.error(400, e.getMessage());
        } catch (IOException e) {
            return Result.error(500, "Failed to read definitions: " + e.getMessage());
        }
    }

    private @NotNull Result<Void> setDefinitionsModel(Realm realm, DefinitionsModel model) {
        try {
            definitionsService.setDefinitions(realm, model);

            return Result.ok();
        } catch (InvalidDefinitionsException e) {
            return Result.error(400, "Invalid definitions. " + e.getMessage());
        }
    }
}
