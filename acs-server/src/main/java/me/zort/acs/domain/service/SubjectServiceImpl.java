package me.zort.acs.domain.service;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.repository.SubjectRepository;
import me.zort.acs.core.domain.mapper.DomainModelMapper;
import me.zort.acs.core.domain.mapper.DomainToPersistenceMapper;
import me.zort.acs.api.domain.provider.SubjectProvider;
import me.zort.acs.api.domain.service.SubjectService;
import me.zort.acs.data.entity.SubjectEntity;
import me.zort.acs.data.id.SubjectId;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.domain.provider.options.SubjectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final DomainModelMapper<Subject, SubjectEntity> subjectMapper;
    private final DomainToPersistenceMapper<Subject, SubjectId> subjectIdMapper;
    private final SubjectProvider subjectProvider;

    @Override
    public Subject createSubject(SubjectType type, String id) {
        Optional<Subject> subjectOptional = getSubject(type, id);

        return subjectOptional.orElseGet(() -> {
            Subject subject = subjectProvider.getSubject(SubjectOptions.builder()
                    .subjectType(type)
                    .id(id).build());

            subject = subjectMapper.toDomain(subjectRepository.save(subjectMapper.toPersistence(subject)));

            return subject;
        });
    }

    @CacheEvict(value = "subjects", key = "#subject.subjectType.id + ':' + #subject.id")
    @Override
    public void deleteSubject(Subject subject) {
        SubjectId id = subjectIdMapper.toPersistence(subject);

        subjectRepository.deleteById(id);
    }

    @Override
    public Optional<Subject> getSubject(SubjectType type, String id) {
        return subjectRepository.findById(new SubjectId(id, type.getId())).map(subjectMapper::toDomain);
    }

    @Override
    public boolean existsSubject(SubjectType type, String id) {
        return subjectRepository.existsById(new SubjectId(id, type.getId()));
    }
}
