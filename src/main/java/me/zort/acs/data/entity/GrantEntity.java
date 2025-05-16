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
    @MapsId("accessorId")
    @JoinColumns({
            @JoinColumn(name = "accessor_id", referencedColumnName = "id"),
            @JoinColumn(name = "accessor_subject_type_id", referencedColumnName = "subject_type_id")
    })
    private SubjectEntity accessor;

    @ManyToOne
    @MapsId("accessedId")
    @JoinColumns({
            @JoinColumn(name = "accessed_id", referencedColumnName = "id"),
            @JoinColumn(name = "accessed_subject_type_id", referencedColumnName = "subject_type_id")
    })
    private SubjectEntity accessed;

    @ManyToOne
    @MapsId("nodeValue")
    @JoinColumn(name = "node_value", referencedColumnName = "value")
    private NodeEntity node;

}
