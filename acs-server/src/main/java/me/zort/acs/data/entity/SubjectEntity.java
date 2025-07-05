package me.zort.acs.data.entity;

import jakarta.persistence.*;
import lombok.*;
import me.zort.acs.api.data.entity.AcsEntity;
import me.zort.acs.data.id.SubjectId;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "acs_subjects")
public class SubjectEntity implements AcsEntity<SubjectId> {
    @EmbeddedId
    private SubjectId id;

    @ManyToOne
    @MapsId("subjectTypeId")
    @JoinColumn(name = "subject_type_id", nullable = false)
    private SubjectTypeEntity subjectType;

    public String getSubjectId() {
        return id.getId();
    }

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

        SubjectEntity that = (SubjectEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id);
    }
}
