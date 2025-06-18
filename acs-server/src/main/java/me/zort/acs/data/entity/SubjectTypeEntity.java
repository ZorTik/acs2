package me.zort.acs.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "acs_subject_types")
public class SubjectTypeEntity {
    @Id
    @Column(length = 128)
    private String id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "acs_subject_types_nodes",
            joinColumns = @JoinColumn(name = "subject_type_id"),
            inverseJoinColumns = @JoinColumn(name = "node_value")
    )
    private Set<NodeEntity> nodes = new HashSet<>();

    @OneToMany(mappedBy = "subjectType", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubjectEntity> subjects = new HashSet<>();

    @OneToMany(mappedBy = "subjectType", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GroupEntity> groups = new HashSet<>();

}
