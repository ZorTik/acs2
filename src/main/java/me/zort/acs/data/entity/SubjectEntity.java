package me.zort.acs.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import me.zort.acs.data.id.SubjectId;

@Data
@Entity
public class SubjectEntity {
    @EmbeddedId
    private SubjectId id;

    @ManyToOne
    @MapsId("subjectTypeId")
    @JoinColumn(name = "subject_type_id", nullable = false)
    private SubjectTypeEntity subjectType;

    public String getSubjectId() {
        return id.getId();
    }
}
