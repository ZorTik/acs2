package me.zort.acs.domain.service;

import lombok.RequiredArgsConstructor;
import me.zort.acs.data.repository.SubjectTypeRepository;
import me.zort.acs.domain.mapper.DomainSubjectTypeMapper;
import me.zort.acs.domain.model.SubjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class SubjectTypeService {
    private final SubjectTypeRepository subjectTypeRepository;
    private final DomainSubjectTypeMapper subjectTypeMapper;

    public Optional<SubjectType> createSubjectType(String id) {
        if (subjectTypeRepository.existsById(id)) {
            return Optional.empty();
        }

        SubjectType subjectType = new SubjectType(id);

        subjectTypeRepository.save(subjectTypeMapper.toPersistence(subjectType));

        return Optional.of(subjectType);
    }

    public boolean deleteSubjectType(SubjectType subjectType) {
        // TODO
    }

    public Optional<SubjectType> getSubjectType(String id) {
        return subjectTypeRepository.findById(id).map(subjectTypeMapper::toDomain);
    }

    public List<SubjectType> getSubjectTypes() {
        return subjectTypeRepository.findAll()
                .stream()
                .map(subjectTypeMapper::toDomain)
                .toList();
    }
}
