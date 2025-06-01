package me.zort.acs.data.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Embeddable
public class SubjectId implements Serializable {
    @Column(name = "id", length = 128)
    private String id;

    @Column(name = "subject_type_id", length = 128)
    private String subjectTypeId;

}
