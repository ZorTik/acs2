package me.zort.acs.plane.http.controller.panel;

import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.definitions.format.DefinitionsFormat;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs.plane.api.domain.realm.Realm;
import me.zort.acs.plane.api.facade.DefinitionsFacade;
import me.zort.acs.plane.api.http.error.HttpError;
import me.zort.acs.plane.api.http.error.HttpErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("/panel/definitions")
@Controller
public class DefinitionsController {
    private final DefinitionsFacade definitionsFacade;
    private final HttpErrorService httpErrorService;

    @PostMapping("/raw")
    public String rawDefinitionsPost(@RequestParam String definitions, Realm realm, Model model) {
        HttpError error = definitionsFacade.setDefinitions(realm, definitions, DefinitionsFormat.YAML);
        httpErrorService.propagate(error, model);

        return rawDefinitionsGet(realm, model);
    }

    @GetMapping("/raw")
    public String rawDefinitionsGet(Realm realm, Model model) {
        DefinitionsModel definitions = realm.getDefinitionsModel();
        model.addAttribute("definitions", DefinitionsFormat.YAML.toStringModel(definitions));

        return "definitions/raw";
    }
}
