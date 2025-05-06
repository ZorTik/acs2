package me.zort.acs.domain.rule;

import lombok.RequiredArgsConstructor;
import me.zort.acs.domain.AccessRequest;
import me.zort.acs.domain.service.DefinitionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class DefaultGrantsRule implements AccessRule {
    private final DefinitionsService definitionsService;

    @Override
    public void onRequest(AccessRequest request) {
        if (definitionsService.checkDefaultGrant(request.getAccessor(), request.getAccessed(), request.getNode())) {
            request.grant();
        }
    }
}
