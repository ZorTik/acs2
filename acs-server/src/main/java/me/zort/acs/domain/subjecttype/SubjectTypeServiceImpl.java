package me.zort.acs.domain.subjecttype;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.repository.SubjectTypeRepository;
import me.zort.acs.api.domain.operation.OperationExecutor;
import me.zort.acs.api.domain.subjecttype.CreateSubjectTypeOptions;
import me.zort.acs.api.domain.subjecttype.SubjectTypeOperationsFactory;
import me.zort.acs.api.domain.subjecttype.exception.SubjectTypeAlreadyExistsException;
import me.zort.acs.core.domain.mapper.DomainModelMapper;
import me.zort.acs.api.domain.provider.SubjectTypeProvider;
import me.zort.acs.api.domain.subjecttype.SubjectTypeService;
import me.zort.acs.data.entity.SubjectTypeEntity;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.domain.provider.options.SubjectTypeOptions;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class SubjectTypeServiceImpl implements SubjectTypeService {
    private final SubjectTypeRepository subjectTypeRepository;
    private final DomainModelMapper<SubjectType, SubjectTypeEntity> subjectTypeMapper;
    private final SubjectTypeProvider subjectTypeProvider;
    private final OperationExecutor<SubjectType> operationExecutor;
    private final SubjectTypeOperationsFactory operationsFactory;

    @NotNull
    @Override
    public SubjectType createSubjectType(String id, CreateSubjectTypeOptions options) {
        Objects.requireNonNull(options, "options cannot be null");

        if (subjectTypeRepository.existsById(id)) {
            throw new SubjectTypeAlreadyExistsException(id);
        }

        SubjectType subjectType = subjectTypeProvider.getSubjectType(SubjectTypeOptions.builder()
                .id(id)
                .nodes(List.of()).build());

        // TODO: přesunout inicializaci do operation
        options.getNodes().forEach(subjectType::addNode);

        subjectType = subjectTypeMapper.toDomain(
                subjectTypeRepository.save(subjectTypeMapper.toPersistence(subjectType)));

        return subjectType;
    }

    @Override
    public void assignNodes(SubjectType subjectType, Collection<Node> nodes) {
        boolean result = operationExecutor.executeOperation(operationsFactory.assignNodes(nodes), subjectType);
        if (!result) {
            return;
        }
    }

    @Override
    public void setSupportsDynamicGroups(SubjectType subjectType, boolean supportsDynamicGroups) {
        boolean result = operationExecutor.executeOperation(
                operationsFactory.changeDynamicGroupsSupport(supportsDynamicGroups), subjectType);
        if (!result) {
            return;
        }

        // TODO: Settings changed
    }

    @Override
    public void deleteSubjectType(String id) {
        subjectTypeRepository.deleteById(id);
    }

    @Override
    public Optional<SubjectType> getSubjectType(String id) {
        return subjectTypeRepository.findById(id).map(subjectTypeMapper::toDomain);
    }

    @Override
    public List<SubjectType> getSubjectTypes() {
        return subjectTypeRepository.findAll()
                .stream()
                .map(subjectTypeMapper::toDomain).toList();
    }
}
