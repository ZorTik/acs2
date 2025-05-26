package me.zort.acs.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
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
    private List<SubjectTypeEntity> subjectTypes = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "acs_groups_nodes",
            joinColumns = @JoinColumn(name = "node_value"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<GroupEntity> groups = new ArrayList<>();
}
