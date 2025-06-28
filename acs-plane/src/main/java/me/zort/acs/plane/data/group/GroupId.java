package me.zort.acs.plane.data.group;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class GroupId {
    @Column(name = "subject_type_realm_id")
    private String subjectTypeRealmId;

    @Column(name = "subject_type_id")
    private String subjectTypeId;

}
