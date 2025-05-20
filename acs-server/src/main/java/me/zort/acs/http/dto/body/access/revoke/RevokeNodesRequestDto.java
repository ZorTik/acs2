package me.zort.acs.http.dto.body.access.revoke;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import me.zort.acs.http.dto.model.subject.SubjectDto;

import java.util.Set;

@Data
public class RevokeNodesRequestDto {
    @Valid
    @NotNull(message = "{validation.accessor.notnull}")
    private SubjectDto sourceSubject;

    @Valid
    @NotNull(message = "{validation.accessed.notnull}")
    private SubjectDto targetSubject;

    @NotEmpty(message = "{validation.nodes.notempty}")
    private Set<String> nodes;

}
