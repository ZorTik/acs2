package me.zort.acs.domain.service;

import lombok.RequiredArgsConstructor;
import me.zort.acs.data.id.SubjectId;
import me.zort.acs.data.repository.SubjectRepository;
import me.zort.acs.domain.mapper.DomainSubjectIdMapper;
import me.zort.acs.domain.mapper.DomainSubjectMapper;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.domain.provider.ModelProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final DomainSubjectMapper subjectMapper;
    private final DomainSubjectIdMapper subjectIdMapper;
    private final ModelProvider modelProvider;

    public void deleteSubject(Subject subject) {
        SubjectId id = subjectIdMapper.toPersistence(subject);

        subjectRepository.deleteById(id);
    }

    public Optional<Subject> getSubject(SubjectType type, String id) {
        return getSubject(type, id, false);
    }

    public Optional<Subject> getSubject(SubjectType type, String id, boolean createIfAbsent) {
        if (createIfAbsent && !existsSubject(type, id)) {
            Subject subject = modelProvider.getSubject(type, id);

            subject = subjectMapper.toDomain(subjectRepository.save(subjectMapper.toPersistence(subject)));

            return Optional.of(subject);
        } else {
            return subjectRepository.findById(new SubjectId(id, type.getId())).map(subjectMapper::toDomain);
        }
    }

    public boolean existsSubject(SubjectType type, String id) {
        return subjectRepository.existsById(new SubjectId(id, type.getId()));
    }
}
