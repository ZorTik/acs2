package me.zort.acs.api.domain.provider;

import me.zort.acs.api.domain.model.Grant;
import me.zort.acs.domain.provider.options.GrantOptions;
import org.jetbrains.annotations.NotNull;

public interface GrantProvider {

    Grant getGrant(final @NotNull GrantOptions options);
}
