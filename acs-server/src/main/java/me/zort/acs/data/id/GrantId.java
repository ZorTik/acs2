package me.zort.acs.data.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class GrantId implements Serializable {
    @Column(name = "accessor_id")
    private String accessorId;

    @Column(name = "accessor_subject_type_id")
    private String accessorSubjectTypeId;

    @Column(name = "accessed_id")
    private String accessedId;

    @Column(name = "accessed_subject_type_id")
    private String accessedSubjectTypeId;

    @Column(name = "node_value")
    private String nodeValue;

}