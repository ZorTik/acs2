package me.zort.acs_plane.http.controller.api;

import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.definitions.format.DefinitionsFormat;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs_plane.api.domain.definitions.DefinitionsService;
import me.zort.acs_plane.api.domain.realm.Realm;
import me.zort.acs_plane.api.http.mapper.HttpFormatMapper;
import me.zort.acs_plane.api.http.mapper.HttpToDomainMapper;
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
    private final DefinitionsService definitionsService;
    private final HttpFormatMapper formatMapper;
    private final HttpToDomainMapper<String, Realm> realmMapper;

    @GetMapping("/realm/{realm}/definitions/v1")
    public String definitions(@RequestHeader("Accept") String acceptHeader, @PathVariable String realm) {
        DefinitionsFormat format = formatMapper.fromMimeType(acceptHeader);
        Realm realmObj = realmMapper.toDomain(realm);

        DefinitionsModel model = definitionsService.getDefinitions(realmObj);

        return format.toStringModel(model);
    }
}
