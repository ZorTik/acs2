package me.zort.acs_plane.domain.definitions;

import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.definitions.exception.InvalidDefinitionsException;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs.core.domain.definitions.validation.DefinitionsValidator;
import me.zort.acs_plane.api.domain.definitions.DefinitionsModification;
import me.zort.acs_plane.api.domain.definitions.DefinitionsModificationService;
import me.zort.acs_plane.api.domain.definitions.DefinitionsObjectFactory;
import me.zort.acs_plane.api.data.definitions.DefinitionsRepository;
import me.zort.acs_plane.api.domain.definitions.DefinitionsService;
import me.zort.acs_plane.api.domain.realm.Realm;
import me.zort.acs_plane.domain.definitions.event.DefinitionsChangedEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class DefinitionsServiceImpl implements DefinitionsService {
    private final DefinitionsRepository definitionsRepository;
    private final DefinitionsValidator definitionsValidator;
    private final DefinitionsObjectFactory modelFactory;
    private final DefinitionsModificationService modificationService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void modifyDefinitions(
            Realm realm, Consumer<DefinitionsModification> modificationAction) throws InvalidDefinitionsException {
        modificationService.modifyDefinitions(realm, modificationAction, defs -> setDefinitions(realm, defs));
    }

    @CacheEvict(value = "definitions-by-realm", key = "#realm.name")
    @Override
    public void setDefinitions(Realm realm, @Nullable DefinitionsModel model) throws InvalidDefinitionsException {
        if (model != null) {
            definitionsValidator.validateDefinitions(model);
        }

        definitionsRepository.saveDefinitions(realm.getName(), model);

        eventPublisher.publishEvent(new DefinitionsChangedEvent(realm, model));
    }

    @Unmodifiable
    @Cacheable(value = "definitions-by-realm", key = "#realm.name")
    @Override
    public DefinitionsModel getDefinitions(Realm realm) {
        realm.requireExists();

        return definitionsRepository.loadDefinitions(realm.getName()).orElse(createEmptyDefs(realm));
    }

    private @NotNull DefinitionsModel createEmptyDefs(Realm realm) {
        DefinitionsModel model = modelFactory.createModel();

        definitionsRepository.saveDefinitions(realm.getName(), model);
        return model;
    }
}
