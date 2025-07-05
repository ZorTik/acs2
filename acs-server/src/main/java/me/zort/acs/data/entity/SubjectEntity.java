package me.zort.acs.data.entity;

import jakarta.persistence.*;
import lombok.*;
import me.zort.acs.core.data.entity.AcsEntity;
import me.zort.acs.core.data.util.HibernateUtil;
import me.zort.acs.data.id.SubjectId;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "acs_subjects")
public class SubjectEntity implements AcsEntity<SubjectId> {
    @EmbeddedId
    private SubjectId id;

    @ManyToOne
    @MapsId("subjectTypeId")
    @JoinColumn(name = "subject_type_id", nullable = false)
    private SubjectTypeEntity subjectType;

    public String getSubjectId() {
        return id.getId();
    }

    @SuppressWarnings("all")
    @Override
    public final boolean equals(Object o) {
        return HibernateUtil.equals(this, o);
    }

    @Override
    public final int hashCode() {
        return HibernateUtil.hashCode(this, true);
    }
}
