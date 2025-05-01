package me.zort.acs.domain.definitions.source;

import lombok.RequiredArgsConstructor;
import me.zort.acs.data.entity.NodeEntity;
import me.zort.acs.data.entity.SubjectTypeEntity;
import me.zort.acs.domain.definitions.definition.SimpleDefinition;
import me.zort.acs.domain.definitions.definition.SubjectTypeDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@RequiredArgsConstructor
public class JpaDefinitionsSource implements DefinitionsSource {
    private final JpaRepository<SubjectTypeEntity, String> subjectTypeRepository;

    @Override
    public List<SubjectTypeDefinition> getSubjectTypes() {
        return subjectTypeRepository.findAll()
                .stream()
                .map(this::mapToDefinition)
                .toList();
    }

    private SubjectTypeDefinition mapToDefinition(SubjectTypeEntity entity) {
        List<String> nodes = entity.getNodes()
                .stream()
                .map(NodeEntity::getValue)
                .toList();

        return new SimpleDefinition(entity.getId(), nodes);
    }
}
