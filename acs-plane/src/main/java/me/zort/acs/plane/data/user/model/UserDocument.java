package me.zort.acs.plane.data.user.model;

import lombok.Data;
import me.zort.acs.plane.api.domain.security.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@Data
@Document(collection = "acs_users")
public class UserDocument {
    @Id
    private UUID id;
    private String displayName;

    @Field
    private Role role = null;

}
