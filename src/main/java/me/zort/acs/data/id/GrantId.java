package me.zort.acs.data.id;

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
    private SubjectId accessorId;
    private SubjectId accessedId;
    private String nodeValue;

}