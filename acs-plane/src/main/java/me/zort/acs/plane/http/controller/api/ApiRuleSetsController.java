package me.zort.acs.plane.http.controller.api;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.realm.Realm;
import me.zort.acs.plane.api.facade.RuleSetFacade;
import me.zort.acs.plane.http.dto.model.ListedRuleSet;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiRuleSetsController {
    private final RuleSetFacade ruleSetFacade;

    @PreAuthorize("hasAuthority('VIEW_RULESETS')")
    @GetMapping("/rulesets")
    public List<ListedRuleSet> listRuleSetsGet(Realm realm) {
        return ruleSetFacade.getRuleSets(realm.getName());
    }

    @GetMapping("/rulesets/download")
    public ResponseEntity<Resource> downloadRuleSetGet(Realm realm, @RequestParam("id") String id) {
        Resource resource = ruleSetFacade.downloadRuleSet(realm.getName(), id);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + id + ".jar" + "\"")
                .body(resource);
    }
}
