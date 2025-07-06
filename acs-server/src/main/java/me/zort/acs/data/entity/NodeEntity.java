package me.zort.acs.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import me.zort.acs.core.data.entity.AcsEntity;
import me.zort.acs.core.data.util.HibernateUtil;

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
    public String getId() {
        return value;
    }

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
