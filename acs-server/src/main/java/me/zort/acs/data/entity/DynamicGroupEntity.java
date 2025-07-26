package me.zort.acs.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.zort.acs.core.data.entity.AcsEntity;
import me.zort.acs.core.data.util.HibernateUtil;
import me.zort.acs.data.id.DynamicGroupId;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "acs_dynamic_groups")
public class DynamicGroupEntity implements AcsEntity<DynamicGroupId> {
    @EmbeddedId
    private DynamicGroupId id;

    @MapsId("subjectTypeId")
    @ManyToOne
    @JoinColumn(name = "subject_type_id", insertable = false, updatable = false)
    private SubjectTypeEntity subjectType;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "subject_id", referencedColumnName = "id", insertable = false, updatable = false),
            @JoinColumn(name = "subject_type_id", referencedColumnName = "subject_type_id", insertable = false, updatable = false)
    })
    private SubjectEntity subject;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "parent_group_id", referencedColumnName = "group_name"),
            @JoinColumn(name = "parent_subject_id", referencedColumnName = "subject_id"),
            @JoinColumn(name = "parent_subject_type_id", referencedColumnName = "subject_type_id")
    })
    private DynamicGroupEntity parent;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "acs_dynamic_groups_nodes",
            joinColumns = {
                    @JoinColumn(name = "group_name", referencedColumnName = "group_name"),
                    @JoinColumn(name = "group_subject_id", referencedColumnName = "subject_id"),
                    @JoinColumn(name = "group_subject_type_id", referencedColumnName = "subject_type_id")
            },
            inverseJoinColumns = @JoinColumn(name = "node_value")
    )
    private Set<NodeEntity> nodes = new HashSet<>();

    public String getName() {
        return id.getName();
    }

    @SuppressWarnings("all")
    @Override
    public final boolean equals(Object o) {
        return HibernateUtil.equals(this, o);
    }

    @Override
    public final int hashCode() {
        return HibernateUtil.hashCode(this, true);
    }
}
