package me.zort.acs.plane.http.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class RegisterForm {
    @NotBlank
    private String username;
    @NotBlank
    private String displayName;
    @NotBlank
    @Size(min = 4, max = 100)
    private String password;

}
