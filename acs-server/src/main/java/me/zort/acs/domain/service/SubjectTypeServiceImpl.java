package me.zort.acs.domain.service;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.repository.SubjectTypeRepository;
import me.zort.acs.api.domain.mapper.DomainModelMapper;
import me.zort.acs.api.domain.service.SubjectTypeService;
import me.zort.acs.data.entity.SubjectTypeEntity;
import me.zort.acs.domain.model.SubjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class SubjectTypeServiceImpl implements SubjectTypeService {
    private final SubjectTypeRepository subjectTypeRepository;
    private final DomainModelMapper<SubjectType, SubjectTypeEntity> subjectTypeMapper;

    @Override
    public Optional<SubjectType> createSubjectType(String id) {
        if (subjectTypeRepository.existsById(id)) {
            return Optional.empty();
        }

        SubjectType subjectType = new SubjectType(id, List.of());

        subjectTypeRepository.save(subjectTypeMapper.toPersistence(subjectType));

        return Optional.of(subjectType);
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
