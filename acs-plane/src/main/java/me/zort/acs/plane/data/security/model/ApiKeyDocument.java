package me.zort.acs.plane.data.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "acs_keys")
public class ApiKeyDocument implements ApiKeyModel {
    @Id
    private int id;

    private String name;
    private String secret;
    private List<String> claims;
}
