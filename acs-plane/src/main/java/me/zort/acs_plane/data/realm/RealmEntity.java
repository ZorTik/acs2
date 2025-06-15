package me.zort.acs_plane.data.realm;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class RealmEntity {
    @Id
    private String id;
    // TODO

}
