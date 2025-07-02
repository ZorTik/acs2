package me.zort.acs.plane.data.definitions.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "acs_realms")
public class RealmDocument {
    @Id
    private String id;

    private List<SubjectTypeDocument> subjectTypes = new ArrayList<>();

    private List<DefaultGrantDocument> defaultGrants = new ArrayList<>();

}
