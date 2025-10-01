package me.zort.acs.plane.http.facade;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.zort.acs.plane.api.domain.ruleset.RuleSet;
import me.zort.acs.plane.api.domain.ruleset.RuleSetParser;
import me.zort.acs.plane.api.domain.ruleset.RuleSetService;
import me.zort.acs.plane.api.domain.storage.BlobObject;
import me.zort.acs.plane.api.domain.storage.BlobPath;
import me.zort.acs.plane.api.domain.storage.BlobStorageService;
import me.zort.acs.plane.api.facade.RuleSetFacade;
import me.zort.acs.plane.http.dto.model.ListedRuleSet;
import me.zort.acs.plane.http.mapper.HttpRuleSetMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RuleSetFacadeImpl implements RuleSetFacade {
    private final RuleSetParser ruleSetParser;
    private final RuleSetService ruleSetService;
    private final HttpRuleSetMapper ruleSetMapper;
    private final BlobStorageService blobStorageService;

    @SneakyThrows(IOException.class)
    @Override
    public void uploadRuleSet(String realmId, MultipartFile file) {
        byte[] data = file.getBytes();

        RuleSet ruleSet = ruleSetParser.parseRuleSet(data);

        BlobPath ruleSetPath = getPathForRuleSet(realmId, ruleSet.getId());
        blobStorageService.store(ruleSetPath, BlobObject.of(data));

        try {
            ruleSetService.registerRuleSet(realmId, ruleSet);
        } catch (Exception e) {
            // Cleanup
            deleteRuleSet(realmId, ruleSet.getId());

            throw e;
        }
    }

    @Override
    public void deleteRuleSet(String realmId, String id) {
        blobStorageService.delete(getPathForRuleSet(realmId, id));

        ruleSetService.unregisterRuleSet(realmId, id);
    }

    @Override
    public List<ListedRuleSet> getRuleSets(String realmId) {
        return ruleSetService.getRuleSets(realmId)
                .stream()
                .map(ruleSetMapper::toHttpListed)
                .toList();
    }

    private BlobPath getPathForRuleSet(String realmId, String id) {
        return BlobPath.compile("/rulesets").append(new BlobPath(new String[] { realmId, id }));
    }
}
