package me.zort.acs.plane.http.controller.panel;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.realm.Realm;
import me.zort.acs.plane.api.facade.RuleSetFacade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/panel/ruleset")
@RequiredArgsConstructor
public class RuleSetsController {
    private final RuleSetFacade ruleSetFacade;

    @PostMapping("/upload")
    public String uploadRuleSet(Realm realm, @RequestParam("rulesets-file") MultipartFile file) {
        ruleSetFacade.uploadRuleSet(realm.getName(), file);

        return "redirect:/panel/realms/edit?realm=" + realm.getName();
    }

    @PostMapping("/delete")
    public String deleteRuleSetGet(Realm realm, @RequestParam String id) {
        ruleSetFacade.deleteRuleSet(realm.getName(), id);

        return "redirect:/panel/realms/edit?realm=" + id;
    }
}
