package me.zort.acs.data.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class SubjectTypeEntity {
    @Id
    private String id;

    @OneToMany(mappedBy = "subjectType")
    private Set<NodeEntity> nodes;

    @OneToMany(mappedBy = "subjectType", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubjectEntity> subjects;
}
