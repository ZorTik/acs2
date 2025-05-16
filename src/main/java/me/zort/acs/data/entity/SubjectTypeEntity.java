package me.zort.acs.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity(name = "acs_subject_types")
public class SubjectTypeEntity {
    @Id
    private String id;

    @ManyToMany(mappedBy = "subjectTypes")
    private Set<NodeEntity> nodes;

    @OneToMany(mappedBy = "subjectType", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubjectEntity> subjects;

}
