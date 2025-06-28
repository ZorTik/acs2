package me.zort.acs.plane.domain.definitions;

import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.ObjectCloner;
import me.zort.acs.core.domain.definitions.exception.InvalidDefinitionsException;
import me.zort.acs.core.domain.definitions.model.*;
import me.zort.acs.plane.api.domain.definitions.DefinitionsObjectFactory;
import me.zort.acs.plane.api.domain.definitions.DefinitionsModification;
import me.zort.acs.plane.api.domain.definitions.DefinitionsModificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class DefinitionsModificationServiceImpl implements DefinitionsModificationService {
    private final DefinitionsObjectFactory objectFactory;
    private final ObjectCloner<DefinitionsModel> objectCloner;

    // TODO: zachytávat modifikace v DefinitionsModificationImpl, pak je vrátit v callbacku a v service broadcastovat
    @Override
    public void modifyDefinitions(
            DefinitionsModel model,
            Consumer<DefinitionsModification> modificationAction,
            Consumer<DefinitionsModel> modifyCallback) throws InvalidDefinitionsException {
        model = objectCloner.cloneObject(model);

        DefinitionsModificationImpl modification = new DefinitionsModificationImpl(objectFactory, model);
        modificationAction.accept(modification);

        modifyCallback.accept(model);
    }
}
