package me.zort.acs.http.controller.v1;

import lombok.RequiredArgsConstructor;
import me.zort.acs.domain.AccessRequest;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.provider.AccessRequestProvider;
import me.zort.acs.domain.service.AccessControlService;
import me.zort.acs.http.dto.body.access.check.AccessCheckRequestDto;
import me.zort.acs.http.dto.body.access.check.AccessCheckResponseDto;
import me.zort.acs.http.mapper.HttpNodeMapper;
import me.zort.acs.http.mapper.HttpSubjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/access")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AccessController {
    private final HttpSubjectMapper subjectMapper;
    private final HttpNodeMapper nodeMapper;
    private final AccessRequestProvider accessRequestProvider;
    private final AccessControlService accessService;

    @PostMapping("/check")
    public AccessCheckResponseDto checkAccess(@RequestBody AccessCheckRequestDto body) {
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
}
