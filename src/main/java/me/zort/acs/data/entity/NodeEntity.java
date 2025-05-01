package me.zort.acs.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class NodeEntity {
    @Id
    private String value;

    @ManyToOne
    @JoinColumn(name = "subject_type_id", nullable = false)
    private SubjectTypeEntity subjectType;

}
