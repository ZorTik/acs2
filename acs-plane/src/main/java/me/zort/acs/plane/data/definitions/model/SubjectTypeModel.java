package me.zort.acs.plane.data.definitions.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SubjectTypeModel {
    private String name;
    private List<NodeModel> nodes = new ArrayList<>();
    private List<GroupModel> groups = new ArrayList<>();

}
