package me.zort.acs.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import me.zort.acs.data.id.GroupId;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "acs_groups")
public class GroupEntity {
    @EmbeddedId
    private GroupId id;

    @MapsId("subjectTypeId")
    @ManyToOne
    @JoinColumn(name = "subject_type_id", insertable = false, updatable = false)
    private SubjectTypeEntity subjectType;

    @Column(name = "group_name", insertable = false, updatable = false)
    private String name;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "parent_group_id", referencedColumnName = "group_name"),
            @JoinColumn(name = "parent_subject_type_id", referencedColumnName = "subject_type_id")
    })
    private GroupEntity parent;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "acs_groups_nodes",
            joinColumns = {
                    @JoinColumn(name = "group_name", referencedColumnName = "group_name"),
                    @JoinColumn(name = "group_subject_type_id", referencedColumnName = "subject_type_id")
            },
            inverseJoinColumns = @JoinColumn(name = "node_value")
    )
    private Set<NodeEntity> nodes = new HashSet<>();

}
