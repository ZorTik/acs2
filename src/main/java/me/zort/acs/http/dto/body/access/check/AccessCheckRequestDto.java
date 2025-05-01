package me.zort.acs.http.dto.body.access.check;

import lombok.Getter;
import me.zort.acs.http.dto.model.subject.SubjectDto;

@Getter
public class AccessCheckRequestDto {
    private SubjectDto accessor;
    private SubjectDto resource;
    private String node;

}
