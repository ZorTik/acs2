package me.zort.acs.http.controller.v1;

import lombok.RequiredArgsConstructor;
import me.zort.acs.domain.service.DefinitionsService;
import me.zort.acs.http.dto.body.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RestController
@RequestMapping("/v1/definitions")
public class DefinitionsController {
    private final DefinitionsService refreshService;

    @PostMapping("/refresh")
    public ResponseEntity<BasicResponse> refresh() {
        refreshService.refresh();

        return ResponseEntity.ok(new BasicResponse("Definitions refreshed successfully"));
    }
}
