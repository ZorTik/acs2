package me.zort.acs.http.controller.v1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.zort.acs.domain.model.*;
import me.zort.acs.domain.service.AccessControlService;
import me.zort.acs.domain.service.GrantService;
import me.zort.acs.http.dto.body.access.check.AccessCheckRequestDto;
import me.zort.acs.http.dto.body.access.check.AccessCheckResponseDto;
import me.zort.acs.http.dto.body.access.grant.GrantNodesRequestDto;
import me.zort.acs.http.dto.body.access.grant.GrantNodesResponseDto;
import me.zort.acs.http.dto.body.access.revoke.RevokeNodesRequestDto;
import me.zort.acs.http.dto.body.access.revoke.RevokeNodesResponseDto;
import me.zort.acs.http.exception.ACSHttpException;
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
    private final AccessControlService accessService;
    private final GrantService grantService;

    @PostMapping("/check")
    public AccessCheckResponseDto checkAccess(@Valid @RequestBody AccessCheckRequestDto body) {
        SubjectLike from = subjectMapper.toDomainOrNull(body.getAccessor());
        SubjectLike to = subjectMapper.toDomainOrNull(body.getResource());

        Map<String, Boolean> states = body.getNodes()
                .stream()
                .collect(Collectors.toMap(Function.identity(), value -> {
                    Node node = nodeMapper.toDomain(value);

                    try {
                        return accessService.checkAccess(from, to, node).isGranted();
                    } catch (IllegalArgumentException e) {
                        throw new ACSHttpException(e.getMessage(), 400);
                    }
                }));

        return new AccessCheckResponseDto(states);
    }

    @PostMapping("/grant")
    public GrantNodesResponseDto grantAccess(@Valid @RequestBody GrantNodesRequestDto body) {
        Subject from = subjectMapper.toDomain(body.getSourceSubject(), true);
        Subject to = subjectMapper.toDomain(body.getTargetSubject(), true);

        Map<String, Boolean> results = body.getNodes()
                .stream()
                .map(nodeMapper::toDomain)
                .collect(Collectors.toMap(
                        Node::getValue, node -> grantService.addGrant(from, to, node).isPresent()));

        return new GrantNodesResponseDto(results);
    }

    @PostMapping("/revoke")
    public RevokeNodesResponseDto revokeAccess(@Valid @RequestBody RevokeNodesRequestDto body) {
        SubjectLike from = subjectMapper.toDomainOrNull(body.getSourceSubject());
        SubjectLike to = subjectMapper.toDomainOrNull(body.getTargetSubject());

        Map<String, Boolean> results = body.getNodes()
                .stream()
                .map(nodeMapper::toDomain)
                .collect(Collectors.toMap(Node::getValue, node ->
                        from instanceof Subject fromSubject && to instanceof Subject toSubject
                                ? grantService.getGrant(fromSubject, toSubject, node)
                                    .map(grantService::removeGrant)
                                    .orElse(false)
                                : false));

        return new RevokeNodesResponseDto(results);
    }
}
