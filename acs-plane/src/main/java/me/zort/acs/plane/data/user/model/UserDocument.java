package me.zort.acs.plane.data.user.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "acs_users")
public class UserDocument {
    @Id
    private UUID id;
    private String displayName;

}
