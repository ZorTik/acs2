package me.zort.acs.data.entity;

import jakarta.persistence.*;
import lombok.*;
import me.zort.acs.core.data.entity.AcsEntity;
import me.zort.acs.core.data.util.HibernateUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "acs_groups")
public class GroupEntity implements AcsEntity<UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "group_name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "subject_type_id", referencedColumnName = "id", insertable = false, updatable = false)
    private SubjectTypeEntity subjectType;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "subject_subject_type_id", referencedColumnName = "subject_type_id", insertable = false, updatable = false),
            @JoinColumn(name = "subject_id", referencedColumnName = "id", insertable = false, updatable = false)
    })
    private SubjectEntity subject;

    @ManyToOne
    @JoinColumn(name = "parent_group_id", referencedColumnName = "id", insertable = false, updatable = false)
    private GroupEntity parent;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "acs_groups_nodes",
            joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "node_value")
    )
    private Set<NodeEntity> nodes = new HashSet<>();

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
