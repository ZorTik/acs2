package me.zort.acs.plane.http.controller.panel;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.definitions.format.DefinitionsFormat;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs.plane.api.domain.realm.Realm;
import me.zort.acs.plane.api.facade.DefinitionsFacade;
import me.zort.acs.plane.api.facade.RealmsFacade;
import me.zort.acs.plane.api.facade.RuleSetFacade;
import me.zort.acs.plane.api.http.error.HttpAlertPropagator;
import me.zort.acs.plane.http.facade.util.Result;
import me.zort.acs.plane.http.dto.model.ListedRealm;
import me.zort.acs.plane.http.dto.realms.RealmsCreateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("/panel/realms")
@Controller
public class RealmsController {
    private final RealmsFacade realmsFacade;
    private final RuleSetFacade ruleSetFacade;
    private final DefinitionsFacade definitionsFacade;
    private final HttpAlertPropagator alertPropagator;

    @GetMapping
    public String listRealmsGet(Model model) {
        List<ListedRealm> realms = realmsFacade.listRealms().orError();

        model.addAttribute("realms", realms);

        return "panel/realms/index";
    }

    @PostMapping("/create")
    public String createRealmPost(@ModelAttribute @Valid RealmsCreateForm form, Model model) {
        String name = form.getName();
        Result<Void> result = realmsFacade.createRealm(name);
        if (result.isError()) {
            alertPropagator.propagateErrorToModel(result.getError(), model);

            return createRealmGet();
        }

        return "redirect:/panel/realms/edit?realm=" + name;
    }

    @GetMapping("/create")
    public String createRealmGet() {
        return "panel/realms/create";
    }

    @PostMapping("/edit")
    public String editRealmPost(@RequestParam String definitions, Realm realm, Model model) {
        Result<Void> result = definitionsFacade.setDefinitions(realm, definitions, "application/x-yaml");
        if (result.isOk()) {
            alertPropagator.propagateAlertToModel("Definitions updated successfully.", model);
        } else {
            alertPropagator.propagateErrorToModel(result.getError(), model);
        }

        model.addAttribute("definitions", definitions);

        return "panel/realms/edit";
    }

    @GetMapping("/edit")
    public String editRealmGet(Realm realm, Model model) {
        DefinitionsModel definitions = realm.getDefinitionsModel();
        model.addAttribute("realmId", realm.getName());
        model.addAttribute("definitions", DefinitionsFormat.YAML.toStringModel(definitions));
        model.addAttribute("ruleSets", ruleSetFacade.getRuleSets(realm.getName()));

        return "panel/realms/edit";
    }

    @GetMapping("/delete")
    public String deleteRealmPost(Realm realm, Model model) {
        Result<Void> result = realmsFacade.deleteRealm(realm.getName());
        if (result.isOk()) {
            alertPropagator.propagateAlertToModel("Realm deleted successfully.", model);
        } else {
            alertPropagator.propagateErrorToModel(result.getError(), model);
        }

        return "redirect:/panel/realms";
    }
}
