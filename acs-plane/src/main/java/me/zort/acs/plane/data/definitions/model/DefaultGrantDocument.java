package me.zort.acs.plane.data.definitions.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DefaultGrantDocument {
    private String from;
    private String to;
    private List<String> nodes = new ArrayList<>();
    private List<String> groups = new ArrayList<>();

}
