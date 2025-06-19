package me.zort.acs.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.zort.acs.api.data.entity.AcsEntity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "acs_nodes")
public class NodeEntity implements AcsEntity<String> {
    @Id
    private String value;

    @Override
    public void setId(String s) {
        this.value = s;
    }
}
