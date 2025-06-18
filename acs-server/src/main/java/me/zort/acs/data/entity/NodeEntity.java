package me.zort.acs.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "acs_nodes")
public class NodeEntity {
    @Id
    private String value;

    @ManyToMany(mappedBy = "nodes")
    private List<SubjectTypeEntity> subjectTypes = new ArrayList<>();

    @ManyToMany(mappedBy = "nodes")
    private List<GroupEntity> groups = new ArrayList<>();
}
