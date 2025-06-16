package me.zort.acs_plane.data.node;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class NodeId {
    @Column(name = "subject_type_realm_id")
    private String subjectTypeRealmId;

    @Column(name = "subject_type_name")
    private String subjectTypeName;

    @Column(name = "value")
    private String value;

}
