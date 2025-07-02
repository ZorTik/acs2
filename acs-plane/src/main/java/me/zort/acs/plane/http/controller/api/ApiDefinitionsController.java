package me.zort.acs.plane.http.controller.api;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.facade.DefinitionsFacade;
import me.zort.acs.plane.api.facade.FacadeOperationsExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("/api")
@Controller
public class ApiDefinitionsController {
    private final DefinitionsFacade definitionsFacade;
    private final FacadeOperationsExecutor operationsExecutor;

    @GetMapping("/realm/{realm}/definitions/v1")
    public String definitionsGet(@RequestHeader("Accept") String acceptHeader, @PathVariable String realm) {
        return operationsExecutor.supply(() -> definitionsFacade.getDefinitions(realm, acceptHeader));
    }
}
