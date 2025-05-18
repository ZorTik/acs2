package me.zort.acs.domain.service;

import lombok.RequiredArgsConstructor;
import me.zort.acs.data.id.SubjectId;
import me.zort.acs.data.repository.SubjectRepository;
import me.zort.acs.domain.mapper.DomainSubjectIdMapper;
import me.zort.acs.domain.mapper.DomainSubjectMapper;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.domain.provider.SubjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final DomainSubjectMapper subjectMapper;
    private final DomainSubjectIdMapper subjectIdMapper;
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
