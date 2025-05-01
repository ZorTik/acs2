package me.zort.acs.domain.service;

import lombok.RequiredArgsConstructor;
import me.zort.acs.domain.definitions.source.DefinitionsSource;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class DefinitionsRefreshService {
    private final SubjectTypeService subjectTypeService;
    private final NodeService nodeService;

    private final DefinitionsSource definitionsSource;

    public void refresh() {
        List<SubjectType> localTypes = subjectTypeService.getSubjectTypes();

        definitionsSource.getSubjectTypes().forEach(def -> {
            String id = def.getId();

            localTypes.removeIf(st -> st.getId().equals(id));

            Optional<SubjectType> subjectTypeOptional = subjectTypeService.createSubjectType(id);
            if (subjectTypeOptional.isEmpty()) {
                return;
            }

            def.getNodes()
                    .forEach(value -> {
                        Optional<Node> nodeOptional = nodeService.createNode(value);
                        if (nodeOptional.isEmpty()) {
                            return;
                        }

                        nodeService.assignNode(nodeOptional.get(), subjectTypeOptional.get());
                    });
        });

        localTypes.forEach(subjectTypeService::deleteSubjectType);
    }
}
