package me.zort.acs.plane.http.dto.realms;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RealmsCreateForm {
    @Pattern(regexp = "^[a-zA-Z0-9]+$") // TODO: Message
    @NotEmpty
    private String name;

}
