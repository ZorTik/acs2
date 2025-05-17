package me.zort.acs.http.controller.v1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.zort.acs.domain.AccessRequest;
import me.zort.acs.domain.model.Grant;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.provider.AccessRequestProvider;
import me.zort.acs.domain.provider.GrantProvider;
import me.zort.acs.domain.service.AccessControlService;
import me.zort.acs.domain.service.GrantService;
import me.zort.acs.http.dto.body.access.check.AccessCheckRequestDto;
import me.zort.acs.http.dto.body.access.check.AccessCheckResponseDto;
import me.zort.acs.http.dto.body.access.grant.GrantNodesRequestDto;
import me.zort.acs.http.dto.body.access.grant.GrantNodesResponseDto;
import me.zort.acs.http.dto.body.access.revoke.RevokeNodesRequestDto;
import me.zort.acs.http.dto.body.access.revoke.RevokeNodesResponseDto;
import me.zort.acs.http.mapper.HttpNodeMapper;
import me.zort.acs.http.mapper.HttpSubjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RestController
@RequestMapping("/v1/access")
public class AccessController {
    private final HttpSubjectMapper subjectMapper;
    private final HttpNodeMapper nodeMapper;
    private final AccessRequestProvider accessRequestProvider;
    private final AccessControlService accessService;
    private final GrantProvider grantProvider;
    private final GrantService grantService;

    @PostMapping("/check")
    public AccessCheckResponseDto checkAccess(@Valid @RequestBody AccessCheckRequestDto body) {
        Subject from = subjectMapper.toDomain(body.getAccessor());
        Subject to = subjectMapper.toDomain(body.getResource());

        Map<String, Boolean> states = body.getNodes()
                .stream()
                .collect(Collectors.toMap(Function.identity(), value -> {
                    Node node = nodeMapper.toDomain(value);

                    AccessRequest accessRequest = accessRequestProvider.getAccessRequest(from, to, node);

                    accessService.checkAccess(accessRequest);

                    return accessRequest.isGranted();
                }));

        return new AccessCheckResponseDto(states);
    }

    @PostMapping("/grant")
    public GrantNodesResponseDto grantAccess(@Valid @RequestBody GrantNodesRequestDto body) {
        Subject from = subjectMapper.toDomain(body.getSourceSubject());
        Subject to = subjectMapper.toDomain(body.getTargetSubject());

        Map<String, Boolean> results = body.getNodes()
                .stream()
                .map(nodeMapper::toDomain)
                .collect(Collectors.toMap(Node::getValue, node -> {
                    Grant grant = grantProvider.getGrant(from, to, node);

                    return grantService.addGrant(grant);
                }));

        return new GrantNodesResponseDto(results);
    }

    @PostMapping("/revoke")
    public RevokeNodesResponseDto revokeAccess(@Valid @RequestBody RevokeNodesRequestDto body) {
        Subject from = subjectMapper.toDomain(body.getSourceSubject());
        Subject to = subjectMapper.toDomain(body.getTargetSubject());

        Map<String, Boolean> results = body.getNodes()
                .stream()
                .map(nodeMapper::toDomain)
                .collect(Collectors.toMap(Node::getValue, node -> {
                    Grant grant = grantProvider.getGrant(from, to, node);

                    return grantService.removeGrant(grant);
                }));

        return new RevokeNodesResponseDto(results);
    }
}
