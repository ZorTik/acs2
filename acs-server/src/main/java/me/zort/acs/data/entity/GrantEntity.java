package me.zort.acs.data.entity;

import jakarta.persistence.*;
import lombok.*;
import me.zort.acs.core.data.entity.AcsEntity;
import me.zort.acs.core.data.util.HibernateUtil;

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

    @SuppressWarnings("all")
    @Override
    public final boolean equals(Object o) {
        return HibernateUtil.equals(this, o);
    }

    @Override
    public final int hashCode() {
        return HibernateUtil.hashCode(this, false);
    }
}
