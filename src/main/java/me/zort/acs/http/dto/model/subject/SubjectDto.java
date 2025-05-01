package me.zort.acs.http.dto.model.subject;

import lombok.Getter;
import me.zort.acs.http.dto.SubjectId;

@Getter
public class SubjectDto implements SubjectId {
    private String group;
    private String id;

}
