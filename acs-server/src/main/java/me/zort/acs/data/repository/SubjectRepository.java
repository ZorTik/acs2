package me.zort.acs.data.repository;

import me.zort.acs.data.entity.SubjectEntity;
import me.zort.acs.data.id.SubjectId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<SubjectEntity, SubjectId> {

}
