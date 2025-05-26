package me.zort.acs.data.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class GroupId implements Serializable {
    @Column(name = "subject_type_id", nullable = false)
    private String subjectTypeId;

    @Column(name = "group_name", nullable = false)
    private String name;

}
