package me.zort.acs.plane.data.definitions.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SubjectTypeDocument {
    private String name;
    private List<NodeDocument> nodes = new ArrayList<>();
    private List<GroupDocument> groups = new ArrayList<>();

}
