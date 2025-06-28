package me.zort.acs.plane.data.subjecttype;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class SubjectTypeId implements Serializable {
    private String realmId;
    private String name;

}
