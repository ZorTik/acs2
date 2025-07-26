package me.zort.acs.plane.domain.definitions.object.model;

import lombok.AllArgsConstructor;
import me.zort.acs.core.domain.definitions.model.SubjectTypeSettingsModel;

@AllArgsConstructor
public class PlaneSubjectTypeSettingsDefinitionModel implements SubjectTypeSettingsModel {
    private final boolean dynamicGroups;

    @Override
    public boolean isDynamicGroupsAllowed() {
        return dynamicGroups;
    }
}
