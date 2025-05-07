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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/access")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AccessController {
    private final HttpSubjectMapper subjectMapper;
    private final HttpNodeMapper nodeMapper;
    private final AccessRequestProvider accessRequestProvider;
    private final AccessControlService accessService;

    @PostMapping("/check")
    public ResponseEntity<AccessCheckResponseDto> checkAccess(@RequestBody AccessCheckRequestDto body) {
        Subject from = subjectMapper.toDomain(body.getAccessor());
        Subject to = subjectMapper.toDomain(body.getResource());

        Node node = nodeMapper.toDomain(body.getNode());

        AccessRequest accessRequest = accessRequestProvider.getAccessRequest(from, to, node);

        accessService.checkAccess(accessRequest);

        return ResponseEntity.ok(new AccessCheckResponseDto(accessRequest.isGranted()));
    }
}
