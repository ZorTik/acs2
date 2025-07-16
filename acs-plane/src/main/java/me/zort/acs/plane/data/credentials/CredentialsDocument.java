package me.zort.acs.plane.data.credentials;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "acs_credentials")
public class CredentialsDocument {

    @Id
    private long id;
    @Indexed(unique = true)
    private long userId;

    @Indexed(unique = true)
    private String username;
    private String passwordHash;
}
