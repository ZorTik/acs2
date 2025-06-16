package me.zort.acs.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity(name = "acs_grants")
public class GrantEntity {
    @Id
    @Column(columnDefinition = "binary(16)")
    private UUID id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(
                    name = "accessor_id",
                    referencedColumnName = "id",
                    columnDefinition = "varchar(128)",
                    insertable = false, updatable = false),
            @JoinColumn(
                    name = "accessor_subject_type_id",
                    referencedColumnName = "subject_type_id",
                    columnDefinition = "varchar(128)",
                    insertable = false, updatable = false)
    })
    private SubjectEntity accessor;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(
                    name = "accessed_id",
                    referencedColumnName = "id",
                    columnDefinition = "varchar(128)",
                    insertable = false, updatable = false),
            @JoinColumn(
                    name = "accessed_subject_type_id",
                    referencedColumnName = "subject_type_id",
                    columnDefinition = "varchar(128)",
                    insertable = false, updatable = false)
    })
    private SubjectEntity accessed;

    @ManyToOne
    @JoinColumn(name = "node_value", referencedColumnName = "value")
    private NodeEntity node = null;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(
                    name = "group_name",
                    referencedColumnName = "group_name",
                    insertable = false, updatable = false),
            @JoinColumn(
                    name = "group_subject_type_id",
                    referencedColumnName = "subject_type_id",
                    insertable = false, updatable = false)
    })
    private GroupEntity group = null;

}
