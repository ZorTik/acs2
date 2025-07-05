package me.zort.acs.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import me.zort.acs.api.data.entity.AcsEntity;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "acs_nodes")
public class NodeEntity implements AcsEntity<String> {
    @Id
    private String value;

    @Override
    public void setId(String s) {
        this.value = s;
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

        NodeEntity that = (NodeEntity) o;
        return getValue() != null && Objects.equals(getValue(), that.getValue());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
