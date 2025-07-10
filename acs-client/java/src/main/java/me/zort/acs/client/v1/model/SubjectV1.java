package me.zort.acs.client.v1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.zort.acs.client.AcsSubjectResolvable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SubjectV1 implements AcsSubjectResolvable {
    private String group;
    private String id;

}
