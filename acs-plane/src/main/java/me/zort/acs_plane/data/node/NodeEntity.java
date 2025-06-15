package me.zort.acs_plane.data.node;

import jakarta.persistence.*;
import me.zort.acs_plane.data.subjecttype.SubjectTypeEntity;

@Entity
public class NodeEntity {
    @EmbeddedId
    private NodeId id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "subject_type_realm_id", referencedColumnName = "realm_id"),
            @JoinColumn(name = "subject_type_name", referencedColumnName = "name")
    })
    private SubjectTypeEntity subjectType;

    @MapsId("value")
    @Column(name = "value", insertable = false, updatable = false)
    private String value;

}
