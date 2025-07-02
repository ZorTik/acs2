package me.zort.acs.plane.data.definitions.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GroupDocument {
    private String id;
    private String parent;
    private List<String> nodes = new ArrayList<>();

}
