package me.zort.acs.plane.data.credentials;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "acs_credentials")
public class CredentialsDocument {

    @Id
    private UUID id;
    @Indexed(unique = true)
    private UUID userId;

    @Indexed(unique = true)
    private String username;
    private String passwordHash;
}
