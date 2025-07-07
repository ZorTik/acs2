package me.zort.acs.plane.data.security.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "acs_api_keys")
public class ApiKeyDocument {
    @Id
    private String key;

}
