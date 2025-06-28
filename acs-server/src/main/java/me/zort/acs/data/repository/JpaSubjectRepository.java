package me.zort.acs.data.repository;

import me.zort.acs.api.data.repository.SubjectRepository;
import me.zort.acs.data.entity.SubjectEntity;
import me.zort.acs.data.id.SubjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSubjectRepository extends JpaRepository<SubjectEntity, SubjectId>, SubjectRepository {
}
