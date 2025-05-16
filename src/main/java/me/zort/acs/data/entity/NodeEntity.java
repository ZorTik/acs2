package me.zort.acs.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "acs_nodes")
public class NodeEntity {
    @Id
    private String value;

    @ManyToMany
    @JoinTable(
            name = "acs_subject_types_nodes",
            joinColumns = @JoinColumn(name = "node_value"),
            inverseJoinColumns = @JoinColumn(name = "subject_type_id")
    )
    private List<SubjectTypeEntity> subjectTypes;

}
