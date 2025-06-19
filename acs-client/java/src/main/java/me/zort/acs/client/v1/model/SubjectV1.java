package me.zort.acs.client.v1.model;

import lombok.Getter;
import me.zort.acs.client.AcsSubjectResolvable;

@Getter
public class SubjectV1 implements AcsSubjectResolvable {
    private String group;
    private String id;

}
