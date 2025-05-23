package me.zort.acs.domain.service;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.repository.SubjectRepository;
import me.zort.acs.api.domain.mapper.DomainModelMapper;
import me.zort.acs.api.domain.mapper.DomainToPersistenceMapper;
import me.zort.acs.api.domain.provider.SubjectProvider;
import me.zort.acs.data.entity.SubjectEntity;
import me.zort.acs.data.id.SubjectId;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final DomainModelMapper<Subject, SubjectEntity> subjectMapper;
    private final DomainToPersistenceMapper<Subject, SubjectId> subjectIdMapper;
    private final SubjectProvider subjectProvider;

    public Optional<Subject> createSubject(SubjectType type, String id) {
        if (existsSubject(type, id)) {
            return Optional.empty();
        }

        Subject subject = subjectProvider.getSubject(type, id);

        subject = subjectMapper.toDomain(subjectRepository.save(subjectMapper.toPersistence(subject)));

        return Optional.of(subject);
    }

    @CacheEvict(value = "subjects", key = "#subject.subjectType.id + ':' + #subject.id")
    public void deleteSubject(Subject subject) {
        SubjectId id = subjectIdMapper.toPersistence(subject);

        subjectRepository.deleteById(id);
    }

    public Optional<Subject> getSubject(SubjectType type, String id) {
        return subjectRepository.findById(new SubjectId(id, type.getId())).map(subjectMapper::toDomain);
    }

    public boolean existsSubject(SubjectType type, String id) {
        return subjectRepository.existsById(new SubjectId(id, type.getId()));
    }
}
