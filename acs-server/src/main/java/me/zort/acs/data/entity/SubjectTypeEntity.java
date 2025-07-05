package me.zort.acs.data.entity;

import jakarta.persistence.*;
import lombok.*;
import me.zort.acs.api.data.entity.AcsEntity;
import org.hibernate.proxy.HibernateProxy;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "acs_subject_types")
public class SubjectTypeEntity implements AcsEntity<String> {
    @Id
    @Column(length = 128)
    private String id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "acs_subject_types_nodes",
            joinColumns = @JoinColumn(name = "subject_type_id"),
            inverseJoinColumns = @JoinColumn(name = "node_value")
    )
    private Set<NodeEntity> nodes = new HashSet<>();

    @OneToMany(mappedBy = "subjectType", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<SubjectEntity> subjects = new HashSet<>();

    @OneToMany(mappedBy = "subjectType", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<GroupEntity> groups = new HashSet<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        SubjectTypeEntity that = (SubjectTypeEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
