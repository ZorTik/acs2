package me.zort.acs.http.controller.v1;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.AccessService;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.http.dto.body.subjects.list.ListSubjectsResponseDto;
import me.zort.acs.http.internal.annotation.SubjectRequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("/v1")
@RestController
public class ResourcesController {
    private final AccessService accessService;

    // TODO: Nejprve synchjronizovat logiku ukládání a načítání definicí z/do databáze
    @GetMapping("/resources/granted")
    public ListSubjectsResponseDto grantedByHolders(
            @SubjectRequestParam("accessor") Subject accessor,
            @RequestParam String subjectType, @RequestParam String[] groups, @RequestParam String[] nodes) {
        // TODO: Implement Pageable parser for query parameters
        return null;
    }
}
