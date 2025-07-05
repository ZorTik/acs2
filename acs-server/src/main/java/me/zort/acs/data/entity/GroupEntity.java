package me.zort.acs.data.entity;

import jakarta.persistence.*;
import lombok.*;
import me.zort.acs.api.data.entity.AcsEntity;
import me.zort.acs.data.id.GroupId;
import org.hibernate.proxy.HibernateProxy;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "acs_groups")
public class GroupEntity implements AcsEntity<GroupId> {
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

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) {
            return false;
        }

        GroupEntity that = (GroupEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id);
    }
}
