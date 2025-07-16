package me.zort.acs.plane.domain.credentials;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.zort.acs.plane.api.domain.credentials.Credentials;

@Data
@AllArgsConstructor
public class CredentialsImpl implements Credentials {
    private final long id;
    private final long userId;

    private String username;
    private String hashedPassword;
}
