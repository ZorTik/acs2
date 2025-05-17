package me.zort.acs.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "acs_subject_types")
public class SubjectTypeEntity {
    @Id
    private String id;

    @ManyToMany(mappedBy = "subjectTypes", fetch = FetchType.EAGER)
    private Set<NodeEntity> nodes = new HashSet<>();

    @OneToMany(mappedBy = "subjectType", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubjectEntity> subjects = new HashSet<>();

}
