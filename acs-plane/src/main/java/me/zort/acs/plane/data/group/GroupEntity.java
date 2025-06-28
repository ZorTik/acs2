package me.zort.acs.plane.data.group;

import jakarta.persistence.*;
import me.zort.acs.plane.data.node.NodeEntity;
import me.zort.acs.plane.data.subjecttype.SubjectTypeEntity;

import java.util.List;

@Entity
public class GroupEntity {
    @EmbeddedId
    private GroupId id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "subject_type_realm_id", referencedColumnName = "realm_id"),
            @JoinColumn(name = "subject_type_name", referencedColumnName = "name")
    })
    private SubjectTypeEntity subjectType;

    @ManyToMany
    @JoinTable(
            name = "groups_nodes",
            joinColumns = {
                    @JoinColumn(name = "group_realm_id", referencedColumnName = "realm_id"),
                    @JoinColumn(name = "group_name", referencedColumnName = "name")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "node_subject_type_realm_id", referencedColumnName = "subjectTypeRealmId"),
                    @JoinColumn(name = "node_subject_type_name", referencedColumnName = "subjectTypeName"),
                    @JoinColumn(name = "node_value", referencedColumnName = "value")
            }
    )
    private List<NodeEntity> nodes;

}
