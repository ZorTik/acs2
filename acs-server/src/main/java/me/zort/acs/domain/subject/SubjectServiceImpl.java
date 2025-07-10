package me.zort.acs.domain.subject;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.repository.SubjectRepository;
import me.zort.acs.api.domain.subject.CreateSubjectArgs;
import me.zort.acs.core.domain.mapper.DomainModelMapper;
import me.zort.acs.core.domain.mapper.DomainToPersistenceMapper;
import me.zort.acs.api.domain.provider.SubjectProvider;
import me.zort.acs.api.domain.subject.SubjectService;
import me.zort.acs.data.entity.SubjectEntity;
import me.zort.acs.data.id.SubjectId;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.provider.options.SubjectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubjectProvider subjectProvider;
    private final DomainModelMapper<Subject, SubjectEntity> subjectMapper;
    private final DomainToPersistenceMapper<Subject.Id, SubjectId> subjectIdMapper;

    @Override
    public Subject createSubject(CreateSubjectArgs createArgs) {
        Optional<Subject> subjectOptional = getSubject(
                Subject.id(createArgs.getId(), createArgs.getSubjectType()));

        return subjectOptional.orElseGet(() -> {
            Subject subject = subjectProvider.getSubject(SubjectOptions.builder()
                    .subjectType(createArgs.getSubjectType())
                    .id(createArgs.getId()).build());

            subject = subjectMapper.toDomain(subjectRepository.save(subjectMapper.toPersistence(subject)));

            return subject;
        });
    }

    @CacheEvict(value = "subjects", key = "#id.subjectTypeId + ':' + #id.id")
    @Override
    public void deleteSubject(Subject.Id id) {
        subjectRepository.deleteById(subjectIdMapper.toPersistence(id));
    }

    @Override
    public Optional<Subject> getSubject(Subject.Id id) {
        return subjectRepository.findById(subjectIdMapper.toPersistence(id)).map(subjectMapper::toDomain);
    }

    @Override
    public boolean existsSubject(Subject.Id id) {
        return subjectRepository.existsById(subjectIdMapper.toPersistence(id));
    }
}
