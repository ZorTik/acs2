package me.zort.acs.http.dto.body.access.revoke;

import lombok.Data;
import me.zort.acs.http.dto.model.subject.SubjectDto;

import java.util.List;

@Data
public class RevokeNodesRequestDto {
    private SubjectDto sourceSubject;
    private SubjectDto targetSubject;

    private List<String> nodes;

}
