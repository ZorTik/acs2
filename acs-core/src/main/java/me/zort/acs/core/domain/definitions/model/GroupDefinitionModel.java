package me.zort.acs.core.domain.definitions.model;

import java.util.List;

public interface GroupDefinitionModel {

    String getName();

    String getParentName();

    List<String> getNodes();
}
