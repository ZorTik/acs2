package me.zort.acs.http.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.definitions.DefinitionsService;
import me.zort.acs.http.dto.body.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Definitions", description = "API for managing definitions")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RestController
@RequestMapping("/v1/definitions")
public class DefinitionsController {
    private final DefinitionsService refreshService;

    @PostMapping("/refresh")
    @Operation(summary = "Refreshes all definitions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Definitions refreshed successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error during refresh")
    })
    public ResponseEntity<BasicResponse> refresh() {
        refreshService.refreshDefinitions();

        return ResponseEntity.ok(new BasicResponse("Definitions refreshed successfully"));
    }
}
