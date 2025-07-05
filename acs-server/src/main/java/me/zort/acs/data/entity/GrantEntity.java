package me.zort.acs.data.entity;

import jakarta.persistence.*;
import lombok.*;
import me.zort.acs.api.data.entity.AcsEntity;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "acs_grants")
public class GrantEntity implements AcsEntity<UUID> {
    @Id
    @Column(columnDefinition = "char(36)")
    private UUID id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(
                    name = "accessor_id",
                    referencedColumnName = "id",
                    columnDefinition = "varchar(128)",
                    nullable = false),
            @JoinColumn(
                    name = "accessor_subject_type_id",
                    referencedColumnName = "subject_type_id",
                    columnDefinition = "varchar(128)",
                    nullable = false)
    })
    private SubjectEntity accessor;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(
                    name = "accessed_id",
                    referencedColumnName = "id",
                    columnDefinition = "varchar(128)",
                    nullable = false),
            @JoinColumn(
                    name = "accessed_subject_type_id",
                    referencedColumnName = "subject_type_id",
                    columnDefinition = "varchar(128)",
                    nullable = false)
    })
    private SubjectEntity accessed;

    @ManyToOne
    @JoinColumn(name = "node_value", referencedColumnName = "value")
    private NodeEntity node = null;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(
                    name = "group_name",
                    referencedColumnName = "group_name",
                    insertable = false,
                    updatable = false),
            @JoinColumn(
                    name = "group_subject_type_id",
                    referencedColumnName = "subject_type_id",
                    insertable = false,
                    updatable = false)
    })
    private GroupEntity group = null;

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

        GrantEntity that = (GrantEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
