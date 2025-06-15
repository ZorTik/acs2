package me.zort.acs_plane.data.definitions;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs_plane.api.data.definitions.DefinitionsPersistenceService;
import me.zort.acs_plane.api.domain.definitions.DefinitionsObjectFactory;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Repository
public class JpaDefinitionsPersistenceService implements DefinitionsPersistenceService {
    @PersistenceContext
    private final EntityManager entityManager;
    private final DefinitionsObjectFactory definitionsObjectFactory;

    @Override
    public void saveDefinitions(String realm, @Nullable DefinitionsModel model) {
        if (model == null) {
            // TODO: Clear

            return;
        }

        // Clear before saving
        saveDefinitions(realm, null);

        // TODO
    }

    @Override
    public Optional<DefinitionsModel> loadDefinitions(String realm) {
        // TODO
        return Optional.empty();
    }
}
