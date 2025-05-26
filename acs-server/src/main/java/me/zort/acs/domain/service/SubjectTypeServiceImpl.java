package me.zort.acs.domain.service;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.repository.SubjectTypeRepository;
import me.zort.acs.api.domain.mapper.DomainModelMapper;
import me.zort.acs.api.domain.provider.SubjectTypeProvider;
import me.zort.acs.api.domain.service.SubjectTypeService;
import me.zort.acs.data.entity.SubjectTypeEntity;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.domain.provider.options.SubjectTypeOptions;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class SubjectTypeServiceImpl implements SubjectTypeService {
    private final SubjectTypeRepository subjectTypeRepository;
    private final DomainModelMapper<SubjectType, SubjectTypeEntity> subjectTypeMapper;
    private final SubjectTypeProvider subjectTypeProvider;

    @NotNull
    @Override
    public SubjectType createSubjectType(String id) {
        if (subjectTypeRepository.existsById(id)) {
            return getSubjectType(id).orElseThrow();
        }

        SubjectType subjectType = subjectTypeProvider.getSubjectType(SubjectTypeOptions.builder()
                .id(id)
                .nodes(List.of()).build());

        subjectTypeRepository.save(subjectTypeMapper.toPersistence(subjectType));

        return subjectType;
    }

    @Override
    public void deleteSubjectType(SubjectType subjectType) {
        subjectTypeRepository.delete(subjectTypeMapper.toPersistence(subjectType));
    }

    @Override
    public Optional<SubjectType> getSubjectType(String id) {
        return subjectTypeRepository.findById(id).map(subjectTypeMapper::toDomain);
    }

    @Override
    public List<SubjectType> getSubjectTypes() {
        return subjectTypeRepository.findAll()
                .stream()
                .map(subjectTypeMapper::toDomain)
                .toList();
    }
}
