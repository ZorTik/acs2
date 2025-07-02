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

    @Override
    public HttpError setDefinitions(Realm realm, String definitions, DefinitionsFormat format) {
        DefinitionsModel model;
        try (InputStream in = new ByteArrayInputStream(definitions.getBytes(StandardCharsets.UTF_8))) {
            model = format.parseModel(in);
        } catch (DefinitionsParseException e) {
            return HttpError.of(400, "Invalid definitions format: " + e.getMessage());
        } catch (IOException e) {
            return HttpError.of(500, "Failed to read definitions: " + e.getMessage());
        }

        try {
            definitionsService.setDefinitions(realm, model);
        } catch (InvalidDefinitionsException e) {
            return HttpError.of(400, "Invalid definitions: " + e.getMessage());
        }

        return null;
    }
}
