package me.zort.acs.core.domain.definitions.format.yaml.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.zort.acs.core.domain.definitions.model.SubjectTypeSettingsModel;

@NoArgsConstructor
@Data
public class YamlTypeSettingsModel implements SubjectTypeSettingsModel {
    private boolean dynamicGroups;

    @Override
    public boolean isDynamicGroupsAllowed() {
        return dynamicGroups;
    }
}
