package me.zort.acs.plane.data.user.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "acs_users")
public class UserDocument {
    @Id
    private long id;
    private String displayName;

}
