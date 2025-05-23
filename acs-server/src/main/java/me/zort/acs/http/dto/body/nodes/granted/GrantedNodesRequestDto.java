package me.zort.acs.http.dto.body.nodes.granted;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import me.zort.acs.http.dto.model.subject.SubjectDto;

@Data
public class GrantedNodesRequestDto {
    @Valid
    @NotNull(message = "{validation.accessor.notnull}")
    private SubjectDto accessor;

    @Valid
    @NotNull(message = "{validation.accessed.notnull}")
    private SubjectDto resource;

}
