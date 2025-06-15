package me.zort.acs_plane.domain.definitions;

import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs_plane.api.data.definitions.DefinitionsRepository;
import me.zort.acs_plane.api.domain.definitions.DefinitionsObjectFactory;
import me.zort.acs_plane.api.domain.definitions.DefinitionsPersistenceService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class DefinitionsPersistenceServiceImpl implements DefinitionsPersistenceService {
    private final DefinitionsRepository definitionsRepository;
    private final DefinitionsObjectFactory modelFactory;

    @CacheEvict(value = "definitions-by-realm", key = "#realmName")
    @Override
    public void saveDefinitions(String realmName, @Nullable DefinitionsModel model) {
        definitionsRepository.saveDefinitions(realmName, model);
    }

    private @NotNull DefinitionsModel createEmptyDefs(String realmName) {
        DefinitionsModel model = modelFactory.createModel();

        definitionsRepository.saveDefinitions(realmName, model);
        return model;
    }

    @Unmodifiable
    @Cacheable(value = "definitions-by-realm", key = "#realmName")
    @Override
    public @NotNull DefinitionsModel loadOrCreateDefinitions(String realmName) {
        return definitionsRepository.loadDefinitions(realmName).orElse(createEmptyDefs(realmName));
    }
}
