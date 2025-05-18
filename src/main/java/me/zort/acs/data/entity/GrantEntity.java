package me.zort.acs.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import me.zort.acs.data.id.GrantId;

@Data
@Entity(name = "acs_grants")
public class GrantEntity {
    @EmbeddedId
    private GrantId id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(
                    name = "accessor_id",
                    referencedColumnName = "id",
                    insertable = false,
                    updatable = false),
            @JoinColumn(
                    name = "accessor_subject_type_id",
                    referencedColumnName = "subject_type_id",
                    insertable = false,
                    updatable = false)
    })
    private SubjectEntity accessor;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(
                    name = "accessed_id",
                    referencedColumnName = "id",
                    insertable = false,
                    updatable = false),
            @JoinColumn(
                    name = "accessed_subject_type_id",
                    referencedColumnName = "subject_type_id",
                    insertable = false,
                    updatable = false)
    })
    private SubjectEntity accessed;

    @ManyToOne
    @MapsId("nodeValue")
    @JoinColumn(name = "node_value", referencedColumnName = "value")
    private NodeEntity node;

}
