package me.zort.acs_plane.data.node;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class NodeId {
    private String subjectTypeRealmId;
    private String subjectTypeName;
    private String value;

}
