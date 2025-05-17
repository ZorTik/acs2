package me.zort.acs.http.dto.body.access.check;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import me.zort.acs.http.dto.model.subject.SubjectDto;

import java.util.List;

@Getter
public class AccessCheckRequestDto {
    @Valid
    @NotNull(message = "{validation.accessor.notnull}")
    private SubjectDto accessor;

    @Valid
    @NotNull(message = "{validation.accessed.notnull}")
    private SubjectDto resource;

    @NotEmpty(message = "{validation.nodes.notempty}")
    private List<String> nodes;

}
