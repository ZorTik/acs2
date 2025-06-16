package me.zort.acs.http.dto.body.subjects.list;

import lombok.AllArgsConstructor;
import me.zort.acs.http.dto.model.subject.SubjectDto;

import java.util.List;

@AllArgsConstructor
public class ListSubjectsResponseDto {
    private List<SubjectDto> subjects;

}
