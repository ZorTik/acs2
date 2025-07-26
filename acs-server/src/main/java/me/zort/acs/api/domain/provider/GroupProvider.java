package me.zort.acs.api.domain.provider;

import me.zort.acs.api.domain.group.Group;
import me.zort.acs.domain.provider.options.GroupOptions;

public interface GroupProvider {

    Group getGroup(GroupOptions options);
}
