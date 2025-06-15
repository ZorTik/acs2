package me.zort.acs_plane.data.group;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class GroupEntity {
    @EmbeddedId
    private GroupId groupId;

    // TODO

}
