package me.zort.acs.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import me.zort.acs.data.id.SubjectId;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "acs_subjects")
public class SubjectEntity {
    @EmbeddedId
    private SubjectId id;

    @ManyToOne
    @MapsId("subjectTypeId")
    @JoinColumn(name = "subject_type_id", nullable = false)
    private SubjectTypeEntity subjectType;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "acs_groups_subjects",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<GroupEntity> groups = new ArrayList<>();

    public String getSubjectId() {
        return id.getId();
    }
}
