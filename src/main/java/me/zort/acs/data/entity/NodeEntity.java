package me.zort.acs.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class NodeEntity {
    @Id
    private String value;

    @ManyToMany
    @JoinTable(
            name = "node_subject_type",
            joinColumns = @JoinColumn(name = "node_value"),
            inverseJoinColumns = @JoinColumn(name = "subject_type_id")
    )
    private Set<SubjectTypeEntity> subjectTypes;
}
