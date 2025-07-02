package me.zort.acs.plane.data.definitions.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "acs_realms")
public class RealmModel {
    @Id
    private String id;

    private List<SubjectTypeModel> subjectTypes = new ArrayList<>();

    private List<DefaultGrantModel> defaultGrants = new ArrayList<>();

}
