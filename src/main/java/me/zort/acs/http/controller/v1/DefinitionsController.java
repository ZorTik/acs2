package me.zort.acs.http.controller.v1;

import lombok.RequiredArgsConstructor;
import me.zort.acs.domain.service.DefinitionsRefreshService;
import me.zort.acs.http.dto.body.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("/v1/definitions")
public class DefinitionsController {
    private final DefinitionsRefreshService refreshService;

    @PostMapping("/refresh")
    public ResponseEntity<BasicResponse> refresh() {
        refreshService.refresh();

        return ResponseEntity.ok(new BasicResponse("Definitions refreshed successfully"));
    }
}
