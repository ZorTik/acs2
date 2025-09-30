package me.zort.acs.plane.api.facade;

import me.zort.acs.plane.http.dto.model.ListedRuleSet;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RuleSetFacade {

    void uploadRuleSet(String realmId, MultipartFile file);

    void deleteRuleSet(String realmId, String id);

    List<ListedRuleSet> getRuleSets(String realmId);
}
