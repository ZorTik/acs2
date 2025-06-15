package me.zort.acs_plane.data.realm;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import me.zort.acs_plane.data.subjecttype.SubjectTypeEntity;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class RealmEntity {
    @Id
    private String id;

    @OneToMany(fetch = FetchType.EAGER)
    private List<SubjectTypeEntity> subjectTypes = new ArrayList<>();

}
