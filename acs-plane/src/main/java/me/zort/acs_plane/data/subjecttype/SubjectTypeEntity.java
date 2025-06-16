package me.zort.acs_plane.data.subjecttype;

import jakarta.persistence.*;
import me.zort.acs_plane.data.realm.RealmEntity;

@Entity
public class SubjectTypeEntity {
    @EmbeddedId
    private SubjectTypeId id;

    @MapsId("realmId")
    @ManyToOne
    @JoinColumn(name = "realm_id", referencedColumnName = "id")
    private RealmEntity realm;

    @MapsId("name")
    @Column(name = "name")
    private String name;

}
