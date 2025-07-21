package me.zort.acs.plane.domain.credentials;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.zort.acs.plane.api.domain.credentials.Credentials;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CredentialsImpl implements Credentials {
    private final UUID id;
    private final UUID userId;

    private String username;
    private String hashedPassword;
}
