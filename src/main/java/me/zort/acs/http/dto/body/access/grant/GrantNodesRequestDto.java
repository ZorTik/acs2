package me.zort.acs.http.dto.body.access.grant;

import lombok.Data;
import me.zort.acs.http.dto.model.subject.SubjectDto;

import java.util.List;

@Data
public class GrantNodesRequestDto {
    private SubjectDto sourceSubject;
    private SubjectDto targetSubject;

    private List<String> nodes;

}
