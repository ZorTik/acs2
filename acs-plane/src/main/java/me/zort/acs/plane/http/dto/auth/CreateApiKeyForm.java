package me.zort.acs.plane.http.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import me.zort.acs.plane.api.domain.security.Privilege;

import java.util.List;

@Data
@Getter
public class CreateApiKeyForm {
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    @NotBlank
    private String name;
    @NotNull
    private List<Privilege> claims;

}
