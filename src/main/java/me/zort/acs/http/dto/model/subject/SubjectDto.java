package me.zort.acs.http.dto.model.subject;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import me.zort.acs.http.dto.SubjectId;

@Getter
public class SubjectDto implements SubjectId {
    @NotNull(message = "{validation.subject.group.notnull}")
    private String group;

    @NotNull(message = "{validation.subject.id.notnull}")
    private String id;

}
