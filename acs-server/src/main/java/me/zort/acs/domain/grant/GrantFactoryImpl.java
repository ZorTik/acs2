package me.zort.acs.domain.grant;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.grant.GrantFactory;
import me.zort.acs.api.domain.grant.RightsHolderTypeRegistry;
import me.zort.acs.api.domain.model.Grant;
import me.zort.acs.domain.provider.options.GrantOptions;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class GrantFactoryImpl implements GrantFactory {
    private final RightsHolderTypeRegistry rightsHolderTypeRegistry;

    @Override
    public @Nullable Grant createGrant(GrantOptions options) {
        return rightsHolderTypeRegistry.castAndCallAdapter(
                options.getRightsHolder(),
                (holder, type) -> type.createGrantFromHolder(holder, options));
    }
}
